package com.example.paymentgateway.presentation.ui.paymentSummary.state

/**
 * The purpose of this class is to make a wrap of the checkoutResult model (Decorator pattern)
 * to simplify the display of the model data in the UI
 */
class CheckoutResultDecorator(private val checkoutResult: CheckoutResultModel) {

    private val map: Map<String, String> = checkoutResult.toMap()

    fun getHeaders(): String {
        return map.keys.joinToString(":\n")
    }

    fun getValues(): String {
        return map.values.joinToString("\n")
    }
}

fun CheckoutResultModel.toMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    map["Reference"] = reference
    map["InternalReference"] = internalReference
    map["Status"] = status
    map["StatusReason"] = statusReason ?: ""
    map["StatusMessage"] = statusMessage?: ""
    map["Currency"] = currency
    map["Total"] = total.toString()
    map["FranchiseName"] = franchiseName ?: ""
    map["Authorization"] = authorization ?: ""
    map["Receipt"] = receipt ?: ""
    map["LastUpdate"] = lastUpdate
    return map
}