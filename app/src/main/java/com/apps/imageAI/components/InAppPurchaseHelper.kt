package com.apps.imageAI.components

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.apps.imageAI.data.model.CreditModel
import com.apps.imageAI.data.model.GPTModel
import com.apps.imageAI.data.repository.FirebaseRepository
import com.apps.imageAI.data.repository.PreferenceRepository
import com.google.common.collect.ImmutableList
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


interface InAppPurchaseHelper
{
    val bundles:StateFlow<List<CreditModel>>
    val processingPurchase:StateFlow<Boolean>
    val purchaseStatus:StateFlow<PurchaseStatus>
   fun billingSetup()
   fun makePurchase(productId:String)
   fun fetchProductsDetails()
   fun restorePurchase(callback:(Boolean)->Unit)
   fun disconnect()


}
class InAppPurchaseHelperImpl @Inject constructor(@ActivityContext private val context: Context, private val firebaseRepo: FirebaseRepository, private val creditHelpers: CreditHelpers, private val preferenceRepository: PreferenceRepository):InAppPurchaseHelper
{
    private val  TAG ="InAppPurchaseHelperImpl"
    private lateinit var billingClient: BillingClient
    private val _bundles = MutableStateFlow<List<CreditModel>>(mutableListOf())
    override val bundles = _bundles.asStateFlow()

    //private val _statusText = MutableStateFlow("Initializing...")
    //val statusText = _statusText.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _productDetails = mutableListOf<ProductDetails>()

    private val _processingPurchase = MutableStateFlow(false)
    override val processingPurchase = _processingPurchase.asStateFlow()

    //private val isSubscriptionMode = true
    private var clientStatus:BillingClientStatus = BillingClientStatus.DISCONNECTED
    private val _purchaseStatus = MutableStateFlow<PurchaseStatus>(PurchaseStatus.None)
    override val purchaseStatus = _purchaseStatus.asStateFlow()

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.OK
                && purchases != null
            ) {
               coroutineScope.launch {
                   for (purchase in purchases) {
                    CoroutineScope(Dispatchers.Main).launch { _processingPurchase.value= true }
                       completePurchase(purchase)
                   }
               }
            } else if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.USER_CANCELED
            ) {
                _purchaseStatus.value = PurchaseStatus.Error("Purchase Canceled")
                CoroutineScope(Dispatchers.Main).launch { _processingPurchase.value= false }
            } else {
                _purchaseStatus.value = PurchaseStatus.Error("Purchase Error")
                CoroutineScope(Dispatchers.Main).launch { _processingPurchase.value= false }
            }
        }
    override fun billingSetup() {

        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        _productDetails.clear()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(
                billingResult: BillingResult
            ) {
                if (billingResult.responseCode ==
                    BillingClient.BillingResponseCode.OK
                ) {
                    clientStatus = BillingClientStatus.CONNECTED

                    querySubProducts()
                    restorePurchase {  }
                    queryPendingPurchases()
                } else {
                    clientStatus = BillingClientStatus.CONNECTION_ERROR
                }
            }

            override fun onBillingServiceDisconnected() {
                clientStatus = BillingClientStatus.DISCONNECTED
            }
        })
    }

    override fun fetchProductsDetails() {
        if (clientStatus == BillingClientStatus.CONNECTED) {
            querySubProducts()
        }
    }

    override fun restorePurchase(callback: (Boolean) -> Unit) {
        if (clientStatus!=BillingClientStatus.CONNECTED)
        {
            callback(false)
            return
        }

        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        var isPurchased: Boolean

        billingClient.queryPurchasesAsync(queryPurchasesParams) { _, purchases ->
            if (purchases.isNotEmpty() && purchases[0].purchaseState == Purchase.PurchaseState.PURCHASED) {
                //Log.e(TAG,"Subscription found size:${purchases.size} state:${purchases[0].purchaseState}")
                //Log.e(TAG,"Subscription obj:${purchases[0]}")
                if (creditHelpers.isCreditsPurchased.value.not()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (firebaseRepo.isLoggedIn())
                        {
                            firebaseRepo.updateCreditPurchasedStatus(true)
                        }/*else{
                            preferenceRepository.updateCreditPurchasedStatus(true)
                        }*/
                    }
                }
                isPurchased = true
            } else {
                //Log.e(TAG,"Subscription not found-- ${creditHelpers.isCreditsPurchased.value}")
                isPurchased = false

                CoroutineScope(Dispatchers.IO).launch {
                    if (firebaseRepo.isLoggedIn())
                    {
                        firebaseRepo.updateCreditPurchasedStatus(false)
                    }/*else{
                        preferenceRepository.updateCreditPurchasedStatus(false)
                    }*/

                    preferenceRepository.setGPTModel(GPTModel.GPT_3_5_TURBO.name)
                }

            }
            callback(isPurchased)
        }
    }

    private suspend fun completePurchase(purchase: Purchase) {

        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            //AppLogger.logE(TAG, "Purchased Acknowledged :${purchase.isAcknowledged}")
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams
                    .newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                val ackResult = billingClient.acknowledgePurchase(acknowledgePurchaseParams)
                if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {//success
                    if (firebaseRepo.isLoggedIn())
                    {
                        firebaseRepo.updateCreditPurchasedStatus(true)
                    }/*else {
                        preferenceRepository.updateCreditPurchasedStatus(true)
                    }*/
                    _purchaseStatus.value = PurchaseStatus.Success
                } else {
                    AppLogger.logE(TAG, " Acknowledged failed")
                    _purchaseStatus.value = PurchaseStatus.Error("Acknowledged failed")
                }

            }

        }


        CoroutineScope(Dispatchers.Main).launch { _processingPurchase.value= false }

    }


    private  fun querySubProducts() {

        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Constants.SUBSCRIPTION_PRODUCT_ID)
                        .setProductType(
                            BillingClient.ProductType.SUBS
                        )
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, productDetailsList ->
            //Log.e(TAG,"billingResult code:${billingResult.responseCode} ${billingResult.debugMessage}")
            if (productDetailsList.isNotEmpty()) {
                //Log.e(TAG,"product: ${productDetailsList.size} ${productDetailsList}")
                _productDetails.add(productDetailsList[0])
                val productDetail = productDetailsList[0]
                val list = mutableListOf<CreditModel>()

                val weeklyPrice =
                    productDetail.subscriptionOfferDetails?.find { it.basePlanId == Constants.WEEKLY_PLAN_ID }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice
                val monthlyPrice =
                    productDetail.subscriptionOfferDetails?.find { it.basePlanId == Constants.MONTHLY_PLAN_ID }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice
                val yearlyPrice =
                    productDetail.subscriptionOfferDetails?.find { it.basePlanId == Constants.YEARLY_PLAN_ID }?.pricingPhases?.pricingPhaseList?.getOrNull(
                        0
                    )?.formattedPrice

                weeklyPrice?.let {
                    list.add(CreditModel(0,0,it,Constants.WEEKLY_PLAN_ID,"Weekly"))
                }
                monthlyPrice?.let {
                    list.add(CreditModel(1,0,it,Constants.MONTHLY_PLAN_ID,"Monthly"))
                }

                yearlyPrice?.let {
                    list.add(CreditModel(2,0,it,Constants.YEARLY_PLAN_ID,"Yearly"))
                }

                if (list.isNotEmpty())
                {
                    _bundles.value = list
                }


            }else{
                Log.e(TAG,"product not found...")
            }

        }
    }

    override fun makePurchase(productId: String) {

        if (_productDetails.isEmpty())
        {
            Log.e(TAG,"Product not available to buy")
            _purchaseStatus.value = PurchaseStatus.Error("Product not available to buy")
            return
        }


            val offerToken = _productDetails[0].subscriptionOfferDetails!!.find { it.basePlanId == productId }!!.offerToken

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(_productDetails[0])
                            .setOfferToken(
                                offerToken
                            )
                            .build()
                    )
                )
                .build()

            _purchaseStatus.value = PurchaseStatus.Started
            billingClient.launchBillingFlow(context as Activity, billingFlowParams)

    }

    private fun queryPendingPurchases(){
        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS )
            .build()
        billingClient.queryPurchasesAsync(queryPurchasesParams){a,b->
            coroutineScope.launch {
                for (purchase in b)
                {
                    CoroutineScope(Dispatchers.Main).launch { _processingPurchase.value= true }
                    completePurchase(purchase)
                }
            }
        }
    }

    override fun disconnect() {
        billingClient.endConnection()
    }
}



enum class BillingClientStatus {
    CONNECTED,DISCONNECTED,CONNECTION_ERROR
}
sealed class PurchaseStatus
{
    data object None:PurchaseStatus()
    data object Started:PurchaseStatus()
    data object Success:PurchaseStatus()
    data class Error(val message:String):PurchaseStatus()
}
