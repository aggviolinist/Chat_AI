package com.apps.imageAI.components

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
private const val TAG ="CreditKeyHelpers"
class ApiKeyHelpers @Inject constructor(private val firestore: FirebaseFirestore) {
    private var apiKey:String =""
    private var keyListener: ListenerRegistration? = null

    private var visionKey:String =""
    private var visionkeyListener:ListenerRegistration? = null

    private var stabilityKey:String =""
    private var stabilitykeyListener:ListenerRegistration? = null
    fun connect()
    {
        if (!isLoggedIn())
            return


        val docRef = firestore.collection(FirebaseConstant.API_KEY_COLLECTION).document(FirebaseConstant.API_KEY_DOCUMENT)
        keyListener?.remove()
        keyListener = docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                e.printStackTrace()
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    apiKey = it["key"] as String
                }
            }
        }

        val visionDocRef = firestore.collection(FirebaseConstant.API_KEY_COLLECTION).document(FirebaseConstant.API_KEY_VISION_DOCUMENT)
        visionkeyListener?.remove()
        visionkeyListener = visionDocRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                e.printStackTrace()
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    visionKey = it["key"] as String
                }
            }
        }

        val stabilityDocRef = firestore.collection(FirebaseConstant.API_KEY_COLLECTION).document(FirebaseConstant.API_KEY_STABILITY_DOCUMENT)
        stabilitykeyListener?.remove()
        stabilitykeyListener = stabilityDocRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    stabilityKey = it["key"] as String
                }
            }
        }
    }


    fun getApiKey():String {
        return if (isLoggedIn()) {
            apiKey
        } else {
            ""
        }
    }

    fun getVisionKey():String  {
        return if (isLoggedIn()) {
            visionKey
        } else {
            ""
        }
    }
    fun getStabilityKey():String {
        return if (isLoggedIn()) {
            stabilityKey
        } else {
            ""
        }
    }

    fun disconnect()
    {
        keyListener?.remove()
        visionkeyListener?.remove()
        stabilitykeyListener?.remove()
    }

    private fun isLoggedIn():Boolean = Firebase.auth.currentUser!=null
}