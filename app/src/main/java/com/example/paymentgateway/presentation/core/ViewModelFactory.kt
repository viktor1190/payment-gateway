package com.example.paymentgateway.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paymentgateway.domain.LoginUseCase
import com.example.paymentgateway.domain.SendCheckoutUseCase
import com.example.paymentgateway.presentation.ui.login.LoginViewModel
import com.example.paymentgateway.presentation.ui.paymentForm.PaymentFormViewModel
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModelPresenterMapper

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory(
    val loginUseCase: LoginUseCase,
    val sendCheckoutUseCase: SendCheckoutUseCase,
    val presenterMapper: CheckoutModelPresenterMapper
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(loginUseCase)
            modelClass.isAssignableFrom(PaymentFormViewModel::class.java) ->
                PaymentFormViewModel(sendCheckoutUseCase, presenterMapper)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
