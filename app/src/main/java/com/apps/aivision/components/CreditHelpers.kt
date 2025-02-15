package com.apps.aivision.components

import android.content.SharedPreferences
import android.util.Log
import com.apps.aivision.data.repository.FirebaseRepository
import com.apps.aivision.data.repository.PreferenceRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

private const val TAG="CreditHelpers"
class CreditHelpers @Inject constructor(private val firestore: FirebaseFirestore, private val firebaseRepository: FirebaseRepository, private val preferenceRepository: PreferenceRepository) {
    private var _credits = MutableStateFlow(0)
    val credits get() = _credits.asStateFlow()
    private var creditListener: ListenerRegistration? = null

    private var _creditsPurchased = MutableStateFlow(false)
    val isCreditsPurchased get() = _creditsPurchased.asStateFlow()

    private var _isFreeCredits = MutableStateFlow(false)
    val isFreeCredits get() = _isFreeCredits.asStateFlow()

    private var sharedPreferencesListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    fun connect()
    {
        if (!isLoggedIn())
            return

       val  id = Firebase.auth.currentUser!!.uid
        CoroutineScope(Dispatchers.IO).launch{
            firebaseRepository.updateServerTS()
        }

        val creditRef = firestore.collection(FirebaseConstant.USERS_COLLECTION).document(id)
        creditListener?.remove()
        creditListener = creditRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                e.printStackTrace()
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    if (it.containsKey(FirebaseConstant.CREDIT_BALANCE_NODE)) {
                        _credits.value =
                            it[FirebaseConstant.CREDIT_BALANCE_NODE].toString().toInt()
                    }
                    if (it.containsKey(FirebaseConstant.IS_ANY_BUNDLE_PURCHASED)) {
                        _creditsPurchased.value =
                            it[FirebaseConstant.IS_ANY_BUNDLE_PURCHASED].toString().toBoolean()
                    }

                  //  if (_creditsPurchased.value.not()) // if not subscribed
                    //{
                       /* CoroutineScope(Dispatchers.IO).launch {
                            AppLogger.logE(TAG,"Server Date:${firebaseRepository.getOrUpdateServerTS()}")
                        }*/
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                        if (it.containsKey(FirebaseConstant.FREE_CREDITS_DATE)) {
                            runCatching {
                                val date = it[FirebaseConstant.FREE_CREDITS_DATE].toString()
                                var serverDate = Calendar.getInstance().time
                                if (it.containsKey(FirebaseConstant.SEVER_TIME_STAMP))
                                {
                                    val ts = it[FirebaseConstant.SEVER_TIME_STAMP] as com.google.firebase.Timestamp
                                   serverDate = sdf.parse(sdf.format(ts.toDate().time))!!

                                }

                                val savedDate = sdf.parse(date)
                                val currentD = sdf.format(serverDate)
                                val currentDate = sdf.parse(currentD)
                                if (savedDate!! < currentDate!!) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        firebaseRepository.updateFreeCreditDate(currentD)
                                        if (_creditsPurchased.value.not())
                                        {
                                            firebaseRepository.incrementCredits(Constants.DAILY_FREE_CREDITS)
                                            _isFreeCredits.value = true
                                        }else{
                                            preferenceRepository.setGPT4DailyCount(0)
                                            preferenceRepository.setVisionDailyCount(0)
                                            preferenceRepository.setGenerationDailyCount(0)
                                        }
                                        Log.e(TAG, "Credits: giving credits regular")
                                    }
                                }
                                //val ts = it[FirebaseConstant.SEVER_TIME_STAMP] as com.google.firebase.Timestamp
                                Log.e(TAG, "Credits: date found :${date} ")
                            }.onFailure { it.printStackTrace() }


                        } else {
                            Log.e(TAG, "Credits: credits date not found")
                            CoroutineScope(Dispatchers.IO).launch {

                                runCatching {
                                    val time = Calendar.getInstance().time
                                    val date = sdf.format(time)
                                    firebaseRepository.updateFreeCreditDate(date)
                                    if (_creditsPurchased.value.not())
                                    {
                                        firebaseRepository.incrementCredits(Constants.DAILY_FREE_CREDITS)
                                        _isFreeCredits.value = true
                                    }else{
                                        preferenceRepository.setGPT4DailyCount(0)
                                        preferenceRepository.setVisionDailyCount(0)
                                        preferenceRepository.setGenerationDailyCount(0)
                                    }
                                    Log.e(TAG, "Credits: giving credits 1st time")
                                }.onFailure { it.printStackTrace() }
                            }
                        }
                   // }


                    //Log.e(TAG, "Credits: ${_credits.value}")
                }
            }
        }

    }

  /*  fun connectAsGuest(){

        loadCredits()
        loadPurchasedStatus()
        checkFreeCredits()

        sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreference, key ->
            AppLogger.logE(TAG,"preference key:${key}")
            when(key)
            {
                PreferenceConstant.CREDITS_COUNT_KEY->{
                    loadCredits()
                }
                PreferenceConstant.IS_PREMIUM_KEY->{
                    loadPurchasedStatus()
                }
                else ->{}
            }
        }
        AppLogger.logE(TAG,"preference called")
        preferenceRepository.getDefaultPreference().registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }*/

    fun disconnect()
    {
        creditListener?.remove()
        sharedPreferencesListener?.let {
            preferenceRepository.getDefaultPreference().unregisterOnSharedPreferenceChangeListener(it)
        }
    }

    private fun isLoggedIn():Boolean = Firebase.auth.currentUser!=null

    fun resetFreeCredits(){
        _isFreeCredits.value = false
    }

   /* private fun loadCredits() { _credits.value = preferenceRepository.getCredits()}
    private fun loadPurchasedStatus(){_creditsPurchased.value = preferenceRepository.getCreditsPurchasedStatus()}
    private fun checkFreeCredits(){
        if (_creditsPurchased.value.not()) // if not subscribed
        {
            val date = preferenceRepository.getFreeCreditDate()
            if (date.isNotEmpty())
            {
                AppLogger.logE(TAG, "Credits: credits date found:${date}")
                runCatching {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val currentD = sdf.format(Calendar.getInstance().time)

                    val savedDate = sdf.parse(date)
                    val currentDate = sdf.parse(currentD)
                    if (savedDate!! < currentDate!!) {
                        CoroutineScope(Dispatchers.IO).launch {
                            preferenceRepository.updateFreeCreditDate(currentD)
                            preferenceRepository.updateCredits(_credits.value+Constants.DAILY_FREE_CREDITS)
                            _isFreeCredits.value = true
                            AppLogger.logE(TAG, "Credits: giving credits regular guest")
                        }
                    }
                }.onFailure { it.printStackTrace() }

            }else{
                AppLogger.logE(TAG, "Credits: credits date not found")
                CoroutineScope(Dispatchers.IO).launch {
                    runCatching {
                        val time = Calendar.getInstance().time
                        val formatter = SimpleDateFormat("yyyy-MM-dd")
                        val date = formatter.format(time)
                        preferenceRepository.updateFreeCreditDate(date)
                        preferenceRepository.updateCredits(Constants.DAILY_FREE_CREDITS)
                        _isFreeCredits.value = true
                        Log.e(TAG, "Credits: giving credits 1st time")
                    }.onFailure { it.printStackTrace() }
                }
            }

        }
    }*/
}