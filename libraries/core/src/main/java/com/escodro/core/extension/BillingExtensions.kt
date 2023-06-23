/*
package com.aimobiapps.onphone.utils.extensions

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.ProductType.INAPP
import com.android.billingclient.api.BillingClient.ProductType.SUBS
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType.PURCHASE
import com.appsflyer.AFInAppEventType.SUBSCRIBE
import com.appsflyer.AppsFlyerLib
import com.google.firebase.crashlytics.internal.model.ImmutableList

fun BillingClient.connect(
    onConnected: () -> Unit = {},
    onFailure: (BillingResult) -> Unit = {},
    onDisconnected: () -> Unit = {}
) {
    startConnection(object : BillingClientStateListener {
        override fun onBillingSetupFinished(result: BillingResult) {
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                onConnected()
            } else {
                onFailure(result)
            }
        }

        override fun onBillingServiceDisconnected() {
            onDisconnected()
        }
    })
}

fun BillingClient.launchBilling(
    activity: Activity,
    details: ProductDetails
) {
    val productDetailsParamsList = listOf(
        BillingFlowParams.ProductDetailsParams.newBuilder().let { params ->
            params.setProductDetails(details)
            val token = details.token()
            if (token != null) {
                params.setOfferToken(token)
            }
            params.build()
        }
    )
    val billingFlowParams = BillingFlowParams.newBuilder()
        .setProductDetailsParamsList(productDetailsParamsList)
        .build()

    launchBillingFlow(
        activity,
        billingFlowParams
    )
}

fun BillingClient.getProductDetailsAsync(
    ids: List<String>,
    type: String,
    callback: (List<ProductDetails>) -> Unit,
    result: (billingResult: BillingResult) -> Unit = {}
) {
    val queryProductDetailsParams =
        QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.from(
                    ids.map { id ->
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(id)
                            .setProductType(type)
                            .build()
                    }
                )
            )
            .build()

    queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
        result.invoke(billingResult)
        callback.invoke(productDetailsList)
    }
}

fun BillingClient.getPurchasesAsync(
    type: String,
    callback: (List<Purchase>) -> Unit,
    result: (billingResult: BillingResult) -> Unit = {}
) {
    val queryProductDetailsParams =
        QueryPurchasesParams.newBuilder()
            .setProductType(type)
            .build()

    queryPurchasesAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
        result.invoke(billingResult)
        callback.invoke(productDetailsList)
    }
}

fun ProductDetails.buildAnalyticParams(purchase: Boolean = false): HashMap<String, Any> =
    hashMapOf(
        AFInAppEventParameterName.REVENUE to priceAmountMicros() / 1_000_000f,
        AFInAppEventParameterName.CONTENT_TYPE to if (purchase) "Coins" else "Subscription",
        AFInAppEventParameterName.CONTENT_ID to productId,
        AFInAppEventParameterName.CURRENCY to priceCurrencyCode()
    )

fun List<ProductDetails>.sendAnalytic(context: Context, purchase: Boolean = false) =
    firstOrNull()?.let { details ->
        AppsFlyerLib.getInstance().logEvent(
            context,
            "${if (purchase) PURCHASE else SUBSCRIBE}${details.productId}",
            details.buildAnalyticParams(purchase)
        )
    }

fun ProductDetails.price(purchase: Boolean = false) =
    if (purchase) oneTimePurchaseOfferDetails?.formattedPrice
    else subscriptionOfferDetails?.first()?.pricingPhases?.pricingPhaseList?.first()?.formattedPrice

fun ProductDetails.priceAmountMicros() = oneTimePurchaseOfferDetails?.priceAmountMicros ?: 0

fun ProductDetails.priceCurrencyCode() = oneTimePurchaseOfferDetails?.priceCurrencyCode ?: 0

fun ProductDetails.token() = subscriptionOfferDetails?.first()?.offerToken

fun purchaseParams(purchase: Boolean = false) =
    QueryPurchasesParams.newBuilder().setProductType(if (purchase) INAPP else SUBS).build()

*/
