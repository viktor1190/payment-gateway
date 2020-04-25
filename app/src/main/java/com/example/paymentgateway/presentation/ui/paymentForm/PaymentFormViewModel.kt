package com.example.paymentgateway.presentation.ui.paymentForm

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentgateway.R
import com.example.paymentgateway.domain.SendCheckoutUseCase
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModel
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModelPresenterMapper
import com.example.paymentgateway.presentation.ui.paymentForm.state.PaymentFormState
import kotlinx.coroutines.launch
import java.util.Locale

const val CARD_DUE_MONTH_AND_YEAR_PATTERN = "^(0?[1-9]|11|12)\\/(\\d{4})\$"

class PaymentFormViewModel(
    private val sendCheckoutUseCase: SendCheckoutUseCase,
    private val presenterMapper: CheckoutModelPresenterMapper
) : ViewModel() {

    private val _paymentForm = MutableLiveData<PaymentFormState>()
    val paymentFormState: LiveData<PaymentFormState> = _paymentForm

    private val _transactionResult = MediatorLiveData<Resource<TransactionStatus>>()
    val transactionResult: LiveData<Resource<TransactionStatus>> = _transactionResult

    // TODO victor.valencia add the observer for the submit response

    fun submit(
        name: String,
        email: String,
        cellphone: String,
        cardNumber: String,
        cardDueMonthAndYear: String,
        cardCvv: String,
        amount: String
    ) {
        if (paymentFormState.value?.isDataValid == true) {
            // TODO victor.valencia use the currency to show it to the user
            val currencyAmount = CurrencyAmount(amount.toFloat(), Currency.getInstance(Locale.getDefault()))
            val checkoutModel = CheckoutModel(name, email, cellphone, cardNumber, cardDueMonthAndYear, cardCvv, currencyAmount)
            viewModelScope.launch {
                _transactionResult.addSource(sendCheckoutUseCase(viewModelScope, presenterMapper.mapToEntity(checkoutModel))) {
                    _transactionResult.value = it
                }
            }
        }
    }

    fun paymentDataChanged(
        name: String,
        email: String,
        cellphone: String,
        cardNumber: String,
        cardDueMonthAndYear: String,
        cardCvv: String,
        amount: String
    ) {
        when {
            !isNameValid(name) -> _paymentForm.value = PaymentFormState(nameError = R.string.paymentForm_invalid_user_name)
            !isEmailValid(email) -> _paymentForm.value = PaymentFormState(emailError = R.string.paymentForm_invalid_user_email)
            !isCellphoneValid(cellphone) -> _paymentForm.value = PaymentFormState(cellphoneError = R.string.paymentForm_invalid_user_cellphone)
            !isCardNumberValid(cardNumber) -> _paymentForm.value = PaymentFormState(cardNumberError = R.string.paymentForm_invalid_card_number)
            !isCardDueMonthAndYearValid(cardDueMonthAndYear) -> _paymentForm.value = PaymentFormState(cardMonthAndYearError = R.string.paymentForm_invalid_card_due_monthYear)
            !isCardCvvValid(cardCvv) -> _paymentForm.value = PaymentFormState(cardCvvError = R.string.paymentForm_invalid_card_cvv)
            !isAmountValid(amount) -> _paymentForm.value = PaymentFormState(amountError = R.string.paymentForm_invalid_amount)
            else -> _paymentForm.value = PaymentFormState(isDataValid = true)
        }
    }

    // Form validators

    private fun isNameValid(name: String): Boolean {
        return name.length > 2
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isCellphoneValid(cellphone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(cellphone).matches()
    }

    private fun isCardNumberValid(cardNumber: String): Boolean {
        return "\\d{10,}".toRegex() matches cardNumber
    }

    private fun isCardDueMonthAndYearValid(cardDueMonthAndYear: String): Boolean {
        return CARD_DUE_MONTH_AND_YEAR_PATTERN.toRegex() matches cardDueMonthAndYear
    }

    private fun isCardCvvValid(cardCvv: String): Boolean {
        return "^\\d{3,}\$".toRegex() matches cardCvv
    }

    private fun isAmountValid(amount: String): Boolean {
        return "^\\d*\\.?\\d*\$".toRegex() matches amount
    }
}
