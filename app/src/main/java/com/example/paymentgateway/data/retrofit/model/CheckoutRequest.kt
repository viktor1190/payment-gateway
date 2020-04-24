package com.example.paymentgateway.data.retrofit.model

import java.math.BigInteger

data class CheckoutRequest(
    val auth: Authentication,
    //val locale: String,
    val payment: Payment,
    val instrument: Instrument,
    val payer: Person
)

data class CheckoutResponse(
    val status: Status,
    val internalReference: BigInteger,
    val reference: String
)