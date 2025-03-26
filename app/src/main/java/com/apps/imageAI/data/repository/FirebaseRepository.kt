package com.apps.imageAI.data.repository

import com.apps.imageAI.components.FirebaseConstant
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.resume

interface FirebaseRepository {
    suspend fun loginToFirebase(token:String):Boolean
    suspend fun loginToFirebase(email:String,password:String):Boolean
    fun isLoggedIn():Boolean

    suspend fun setUpAccount()

    suspend fun decrementCredits(amount:Int)
    suspend fun incrementCredits(amount:Int)
    suspend fun updateCreditPurchasedStatus(status:Boolean)
    suspend fun updateFreeCreditDate(date:String)
    suspend fun deleteAccount():Boolean
    suspend fun updateServerTS()
}



class FirebaseRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirebaseRepository {

    override fun isLoggedIn(): Boolean = Firebase.auth.currentUser!=null
    override suspend fun loginToFirebase(token: String):Boolean = suspendCancellableCoroutine {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                it.resume(true)
            }else{
                task.exception?.printStackTrace()
                it.resume(false)
            }
        }

    }

    override suspend fun loginToFirebase(email: String, password: String): Boolean = suspendCancellableCoroutine {
        Firebase.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                it.resume(true)
            }else{
                task.exception?.printStackTrace()
                Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        etask ->
                    if (etask.isSuccessful)
                    {
                        it.resume(true)
                    }else{
                        etask.exception?.printStackTrace()
                        it.resume(false)
                    }
                }

            }
        }

    }

    override suspend fun setUpAccount() {
        /* if (!isLoggedIn())
             return*/
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val email = Firebase.auth.currentUser!!.email
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)
            val snapshot = docRef.get().await()
            if (snapshot != null && snapshot.exists()) {
                val balance = snapshot.data?.get(FirebaseConstant.CREDIT_BALANCE_NODE)
            } else {
                var date ="${System.currentTimeMillis()}"
                runCatching {
                    val time = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    date = formatter.format(time)
                }.onFailure { it.printStackTrace() }
                val data = hashMapOf(
                    FirebaseConstant.IS_ANY_BUNDLE_PURCHASED to false,
                    "email" to email,
                    "date" to date
                )

                docRef.set(data).await()

            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun decrementCredits(amount: Int) {
        if (!isLoggedIn())
            return
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)


            docRef.update(FirebaseConstant.CREDIT_BALANCE_NODE, FieldValue.increment( amount.toLong() * (-1))).await()

        }.onFailure { it.printStackTrace() }
    }

    override suspend fun incrementCredits(amount: Int) {
        decrementCredits((-1)*amount)
    }

    override suspend fun updateCreditPurchasedStatus(status: Boolean) {
        if (!isLoggedIn())
            return
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)

            docRef.update(FirebaseConstant.IS_ANY_BUNDLE_PURCHASED,status).await()
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun updateFreeCreditDate(date: String) {
        if (!isLoggedIn())
            return
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)

            docRef.update(FirebaseConstant.FREE_CREDITS_DATE,date).await()
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun deleteAccount():Boolean {
        if (!isLoggedIn())
            return false
        var isDeleteSuccess = false
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)
            docRef.delete().await()
            Firebase.auth.currentUser!!.delete().await()
            isDeleteSuccess = true

        }.onFailure {
            it.printStackTrace()
            isDeleteSuccess = false
        }
        return isDeleteSuccess
    }

    override suspend fun updateServerTS() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        runCatching {
            val uid = Firebase.auth.currentUser!!.uid
            val docRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(uid)
            docRef.update(FirebaseConstant.SEVER_TIME_STAMP,FieldValue.serverTimestamp()).await()
        }.onFailure {
            it.printStackTrace()

        }

    }
}