package com.example.paymentgateway.data.retrofit.model

import java.math.BigDecimal
import java.math.BigInteger

data class StatusRequest(
    val auth: Authentication,
    //val locale: String,
    val internalReference: BigInteger
)

data class StatusResponse(
    val reference: String,
    val status: Status,
    val internalReference: BigDecimal?,
    val receipt: String?,
    val amount: Amount,
    val paymentMethod: String?,
    val franchiseName: String?,
    val authorization: String?
)