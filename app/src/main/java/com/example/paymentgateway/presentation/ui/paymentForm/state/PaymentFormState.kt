package com.example.paymentgateway.presentation.ui.paymentForm.state

data class PaymentFormState(
    val nameError: Int? = null,
    val emailError: Int? = null,
    val cellphoneError: Int? = null,
    val cardNumberError: Int? = null,
    val cardMonthAndYearError: Int? = null,
    val cardCvvError: Int? = null,
    val amountError: Int? = null,
    val isDataValid: Boolean = false
)