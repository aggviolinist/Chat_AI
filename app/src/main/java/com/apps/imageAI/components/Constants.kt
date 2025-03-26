package com.apps.imageAI.components

import com.apps.imageAI.data.model.GenerationModel
import com.apps.imageAI.data.model.VisionGenerationType

object Constants {
    const val BASE_URL = "https://api.openai.com/v1/"
    const val WEB_CLIENT_ID = "430708282995-jiuk43lpurc6s8h4tk3nr5k7sodmpmsu.apps.googleusercontent.com"
    const val REWARDED_AD_UNIT_ID = "1200622345064808_1201253401668369"
    const val BANNER_AD_UNIT_ID = "1200622345064808_1200635331730176"
    const val BANNER_AD_UNIT_ID2 = "1200622345064808_1200635331730176"
    const val INTERSTITIAL_AD_UNIT_ID = "1200622345064808_1201241478336228"
    const val PRIVACY_POLICY = "https://doc-hosting.flycricket.io/imageai-privacy-policy/5b91bdf7-2298-475d-96a9-8955c079133f/privacy"
    const val TERMS_SERVICE = "https://doc-hosting.flycricket.io/imageai-terms-of-service/e9f8a945-e490-41b9-946a-64d4f18ee76d/terms"

    const val DAILY_FREE_CREDITS=3
    const val CHAT_MESSAGE_COST = 1
    const val CHAT_MESSAGE_GPT4_COST = 2
    const val BASE_IMAGE_GEN_COST = 2
    const val MESSAGES_WORDS_TURBO = 500
    const val MESSAGES_WORDS_GPT4 = 150

    const val SUBSCRIPTION_PRODUCT_ID = "aivision_sub"
    const val WEEKLY_PLAN_ID = "aivision-sub-weekly"
    const val MONTHLY_PLAN_ID = "aivision-sub-monthly"
    const val YEARLY_PLAN_ID = "aivision-sub-yearly"

   /* const val VISION_API_KEY ="" // Required for guest mode only and if imageInput mode is Astica
    const val GPT_API_KEY =""  // Required for guest mode only
    const val STABILITY_API_KEY =""  // Required for guest mode only*/
    val SignInMode = SignInType.Both
    val VisionPlatform = VisionGenerationType.ASTICA
    val ImageGenerationPlatform = GenerationModel.STABILITY
    const val IS_IMAGE_INPUT_PAID = false
    const val EMAIL_DOMAIN = "@guest.com"
    const val ENABLED_PDF_FEATURE = true
    const val MAX_PDF_PAGES_PER_FILE = 10

    // Premium plan daily usage limits
    const val MAX_VISION_LIMIT_PER_DAY =50
    const val MAX_IMAGE_GEN_LIMIT_PER_DAY=50
    const val MAX_GPT4_MESSAGE_LIMIT_PER_DAY=50
}