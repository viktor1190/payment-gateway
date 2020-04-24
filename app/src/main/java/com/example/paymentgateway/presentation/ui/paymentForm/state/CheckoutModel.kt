package com.example.paymentgateway.presentation.ui.paymentForm.state

import android.icu.util.CurrencyAmount

data class CheckoutModel(
    val name: String,
    val email: String,
    val cellphone: String,
    val cardNumber: String,
    val cardDueMonthAndYear: String,
    val cardCvv: String,
    val amount: CurrencyAmount
)