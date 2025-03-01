package com.apps.imageAI.ui.ui_components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.apps.imageAI.components.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdView(adId:String= Constants.BANNER_AD_UNIT_ID) {
    AndroidView(
        modifier = Modifier.defaultMinSize(minHeight = 54.dp)
            .fillMaxWidth().padding(top = 8.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                // Add your adUnitID, this is for testing.
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}