package com.example.paymentgateway.data.retrofit.model

import java.time.LocalDateTime

data class Authentication(
    val login: String,
    val tranKey: String,
    val nonce: String,
    val seed: String
)

data class Payment(
    val reference: String,
    val amount: Amount
)

data class Amount(
    val currency: String,
    val total: Float
)

data class Instrument(
    val card: Card
)

data class Card(
    val number: String,
    val expirationMonth: String,
    val expirationYear: String,
    val cvv: String
)

data class Person(
    val name: String,
    val email: String,
    val mobile: String

)

// Response
data class Status(
    val status: String,
    val reason: String,
    val message: String,
    val date: LocalDateTime
)