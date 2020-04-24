package com.example.paymentgateway.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paymentgateway.data.LoginDataSource
import com.example.paymentgateway.data.LoginRepository
import com.example.paymentgateway.presentation.ui.login.LoginViewModel
import com.example.paymentgateway.presentation.ui.paymentForm.PaymentFormViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(
                    loginRepository = LoginRepository(
                        dataSource = LoginDataSource()
                    )
                )
            modelClass.isAssignableFrom(PaymentFormViewModel::class.java) ->
                PaymentFormViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
