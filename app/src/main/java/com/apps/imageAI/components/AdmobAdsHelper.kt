package com.apps.imageAI.components

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

private const val TAG = "AdmobAdsHelper"

var rewardedAd: RewardedAd? = null

fun loadRewarded(context: Context ,onLoadedOrFailed:(errorMsg:String?)->Unit,onRewarded: () -> Unit) {
    RewardedAd.load(context, Constants.REWARDED_AD_UNIT_ID,
        AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                Log.d("TAG", "Ad was loaded.")
                rewardedAd = ad
                onLoadedOrFailed(null)
                showRewarded(context,onRewarded)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError.toString().let { Log.d("TAG", it) }
                onLoadedOrFailed(adError.toString())
                rewardedAd = null
            }
        })

}


fun showRewarded(context: Context, onRewarded: () -> Unit) {
    val activity = context.findActivity()

    if (rewardedAd != null && activity != null) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                rewardedAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
            }
        }
        rewardedAd?.show(activity) {
            Log.e("AdsHelper","give rewards")
            onRewarded()
        }
    }
}


private var interstitialAd: InterstitialAd? = null
private var adIsLoading: Boolean = false

fun loadAdInters(context: Context){

    // Request a new ad if one isn't already loaded.
    if (adIsLoading || interstitialAd != null) {
        return
    }
    adIsLoading = true

    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
        context,
        Constants.INTERSTITIAL_AD_UNIT_ID ,
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.message)
                interstitialAd = null
                adIsLoading = false

            }
            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                interstitialAd = ad
                adIsLoading = false
            }
        }
    )

}

fun displayIntersAd(context: Context){
    if (interstitialAd != null) {
        interstitialAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Ad was dismissed.")
                    interstitialAd = null
                    loadAdInters(context)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(TAG, "Ad failed to show.")
                    interstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                }


            }
        interstitialAd?.show(context as Activity)
    } else {
        loadAdInters(context)
    }

}

