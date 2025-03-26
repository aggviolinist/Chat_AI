package com.apps.imageAI.ui.ui_components

import android.widget.Toast
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.apps.imageAI.components.Constants.BANNER_AD_UNIT_ID
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdSize
import com.facebook.ads.AdView

//@Composable
//fun BannerAdView(adId: String = BANNER_AD_UNIT_ID) {
//    AndroidView(
//        modifier = Modifier
//            .defaultMinSize(minHeight = 50.dp)
//            .fillMaxWidth()
//            .padding(top = 8.dp),
//        factory = { context ->
//            AdView(context, adId, AdSize.BANNER_HEIGHT_50).apply {
//                // Set up an AdListener for callbacks
//                val adListener = object : AdListener {
//                    override fun onError(ad: Ad?, adError: AdError?) {
//                        // Handle ad error callback
//                        Toast.makeText(
//                            context,
//                            "Ad Error: ${adError?.errorMessage}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    override fun onAdLoaded(ad: Ad?) {
//                        // Handle ad loaded callback (optional)
//                    }
//
//                    override fun onAdClicked(ad: Ad?) {
//                        // Handle ad clicked callback (optional)
//                    }
//
//                    override fun onLoggingImpression(ad: Ad?) {
//                        // Handle ad impression logged callback (optional)
//                    }
//                }
//
//                // Load the ad with the listener configured
//                loadAd(buildLoadAdConfig().withAdListener(adListener).build())
//            }
//        },
//        update = { adView ->
//            // Optional update logic if needed in Compose recompositions
//        }
//    )
//}
@Composable
fun BannerAdView(adId: String = BANNER_AD_UNIT_ID) {
    AndroidView(
        modifier = Modifier
            .defaultMinSize(minHeight = 50.dp)
            .fillMaxWidth()
            .padding(top = 8.dp),
        factory = { context ->
            AdView(context, adId, AdSize.BANNER_HEIGHT_50).apply {
                loadAd()
            }
        }
    )
}