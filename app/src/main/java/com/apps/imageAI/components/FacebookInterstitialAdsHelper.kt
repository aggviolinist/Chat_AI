package com.apps.imageAI.components

import android.app.Activity
import android.content.Context
import android.util.Log
import com.facebook.ads.*

private const val TAG = "AdmobAdsHelper"

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
                    Log.d(TAG, "Interstitial ad displayed.")
                }

                override fun onInterstitialDismissed(ad: Ad) {
                    Log.d(TAG, "Interstitial ad dismissed.")
                    interstitialAd = null
                    adIsLoading = false
                    loadAdInters(context)
                }

                override fun onError(ad: Ad?, adError: AdError?) {
                    Log.d(TAG, "Interstitial ad failed to load: ${adError?.errorMessage}")
                    interstitialAd = null
                    adIsLoading = false
                }

                override fun onAdLoaded(ad: Ad) {
                    Log.d(TAG, "Interstitial ad loaded.")
                    adIsLoading = false
                }

                override fun onAdClicked(ad: Ad) {
                    Log.d(TAG, "Interstitial ad clicked.")
                }

                override fun onLoggingImpression(ad: Ad) {
                    Log.d(TAG, "Interstitial ad impression logged.")
                }
            })
            ?.build()
    )
}

fun displayIntersAd(context: Context) {
    if (interstitialAd != null && interstitialAd!!.isAdLoaded()) {
        interstitialAd!!.show()
    } else {
        loadAdInters(context)
    }
}
