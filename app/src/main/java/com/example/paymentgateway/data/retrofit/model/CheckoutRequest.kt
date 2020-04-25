package com.example.paymentgateway.data.retrofit.model

data class CheckoutRequest(
    val auth: Authentication,
    //val locale: String,
    val payment: Payment,
    val instrument: Instrument,
    val payer: Person
)