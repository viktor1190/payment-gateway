package com.example.paymentgateway.domain.entity

data class Transaction (
    val reference: String,
    val currency: String,
    val total: Float,
    val cardNumber: String,
    val cardExpirationMonth: String,
    val cardExpirationYear: String,
    val cardCvv: String,
    val payerName: String,
    val payerEmail: String,
    val payerMobile: String
)