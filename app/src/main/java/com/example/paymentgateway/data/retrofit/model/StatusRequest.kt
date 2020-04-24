package com.example.paymentgateway.data.retrofit.model

import java.math.BigInteger

data class StatusRequest(
    val auth: Authentication,
    val locale: String,
    val internalReference: BigInteger
)

data class StatusResponse(
    val status: Status,
    val internalReference: BigInteger,
    val reference: String
)