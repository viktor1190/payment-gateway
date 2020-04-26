package com.example.paymentgateway.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paymentgateway.domain.GetTransactionStatusListUseCase
import com.example.paymentgateway.domain.LoginUseCase
import com.example.paymentgateway.domain.SendCheckoutUseCase
import com.example.paymentgateway.presentation.ui.login.LoginViewModel
import com.example.paymentgateway.presentation.ui.paymentForm.PaymentFormViewModel
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModelPresenterMapper
import com.example.paymentgateway.presentation.ui.paymentList.TransactionStatusListViewModel
import com.example.paymentgateway.presentation.ui.paymentSummary.PaymentSummaryViewModel
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModelPresenterMapper

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory(
    val loginUseCase: LoginUseCase,
    val sendCheckoutUseCase: SendCheckoutUseCase,
    val getTransactionStatusListUseCase: GetTransactionStatusListUseCase,
    val presenterMapper: CheckoutModelPresenterMapper,
    val checkoutResponseMapper: CheckoutResultModelPresenterMapper
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(loginUseCase)
            modelClass.isAssignableFrom(PaymentFormViewModel::class.java) ->
                PaymentFormViewModel(sendCheckoutUseCase, presenterMapper)
            modelClass.isAssignableFrom(PaymentSummaryViewModel::class.java) ->
                PaymentSummaryViewModel()
            modelClass.isAssignableFrom(TransactionStatusListViewModel::class.java) ->
                TransactionStatusListViewModel(getTransactionStatusListUseCase, checkoutResponseMapper)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
