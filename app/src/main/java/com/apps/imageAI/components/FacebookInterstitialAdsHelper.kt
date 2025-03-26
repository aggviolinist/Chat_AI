package com.apps.imageAI.components

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import com.facebook.ads.*

private const val TAG = "FacebookInterstitialAdsHelper"

private var interstitialAd: com.facebook.ads.InterstitialAd? = null
private var adIsLoading: Boolean = false

fun loadAdInters(context: Context) {
    // Request a new ad if one isn't already loaded.
    if (adIsLoading || interstitialAd != null) {
        return
    }
    adIsLoading = true

    interstitialAd = com.facebook.ads.InterstitialAd(context, Constants.INTERSTITIAL_AD_UNIT_ID)
    interstitialAd?.loadAd(
        interstitialAd?.buildLoadAdConfig()
            ?.withAdListener(object : InterstitialAdListener {
                override fun onInterstitialDisplayed(ad: Ad) {
                    Log.d(TAG, "Ad was displayed.")
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    Log.d(TAG, "Ad was dismissed.")
                    interstitialAd = null
                    adIsLoading = false
                    loadAdInters(context)
                }

                override fun onError(ad: Ad?, adError: AdError?) {
                    Log.d(TAG, adError?.errorMessage ?: "Unknown error")
                    interstitialAd = null
                    adIsLoading = false
                }

                override fun onAdLoaded(ad: Ad) {
                    Log.d(TAG, "Ad was loaded.")
                    adIsLoading = false
                }

                override fun onAdClicked(ad: Ad) {
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onLoggingImpression(ad: Ad) {
                    Log.d(TAG, "Ad impression logged.")
                }
            })
            ?.build()
    )
}

fun displayIntersAd(context: Context) {
    if (interstitialAd != null && interstitialAd!!.isAdLoaded) {
        interstitialAd?.show()
    } else {
        loadAdInters(context)
    }
}

// Declare the variable for the Facebook rewarded interstitial ad
var rewardedInterstitialAd: RewardedInterstitialAd? = null

// Load the rewarded interstitial ad
fun loadRewarded(context: Context, onLoadedOrFailed: (errorMsg: String?) -> Unit, onRewarded: () -> Unit) {
    // Initialize the rewarded interstitial ad with your placement ID
    rewardedInterstitialAd = RewardedInterstitialAd(context, Constants.REWARDED_AD_UNIT_ID)

    // Set up the ad listener
    val adListener = object : RewardedInterstitialAdListener {
        override fun onError(ad: Ad, error: AdError) {
            // Handle error when ad fails to load
            onLoadedOrFailed(error.errorMessage)
            rewardedInterstitialAd = null
        }

        override fun onAdLoaded(ad: Ad) {
            Log.d("TAG", "Ad was loaded.")
            // Notify that the ad is loaded and ready to show
            onLoadedOrFailed(null)
            showRewardedInterstitial(context, onRewarded)
        }

        override fun onAdClicked(ad: Ad) {
            Log.d("TAG", "Rewarded interstitial ad clicked!")
        }

        override fun onLoggingImpression(ad: Ad) {
            Log.d("TAG", "Rewarded interstitial ad impression logged!")
        }

        override fun onRewardedInterstitialCompleted() {
            Log.d("TAG", "Rewarded interstitial completed!")
            // Call the reward function when the ad is completed
            onRewarded()
        }

        override fun onRewardedInterstitialClosed() {
            rewardedInterstitialAd = null
            Log.d("TAG", "Rewarded interstitial ad closed!")
        }
    }

    // Load the ad with the listener
    rewardedInterstitialAd?.loadAd(
        rewardedInterstitialAd?.buildLoadAdConfig()
            ?.withAdListener(adListener)
            ?.build()
    )
}

// Show the rewarded interstitial ad
fun showRewardedInterstitial(context: Context, onRewarded: () -> Unit) {
    val activity = context.findActivity()

    if (rewardedInterstitialAd != null && activity != null) {
        rewardedInterstitialAd?.show(
            rewardedInterstitialAd?.buildShowAdConfig()
                ?.withAppOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                ?.build()
        )
    }
}
