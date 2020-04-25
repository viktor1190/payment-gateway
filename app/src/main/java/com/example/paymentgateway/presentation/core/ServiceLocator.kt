package com.example.paymentgateway.presentation.core

import com.example.paymentgateway.data.LoginDataSource
import com.example.paymentgateway.data.TransactionRepositoryImpl
import com.example.paymentgateway.data.UserRepositoryImpl
import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import com.example.paymentgateway.data.retrofit.util.CheckoutModelDataMapper
import com.example.paymentgateway.data.retrofit.util.LiveDataCallAdapterFactory
import com.example.paymentgateway.domain.GetCurrentUserUseCase
import com.example.paymentgateway.domain.LoginUseCase
import com.example.paymentgateway.domain.SendCheckoutUseCase
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModelPresenterMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceLocator {

    val viewModelFactory by lazy { ViewModelFactory(loginUseCase, sendCheckoutUseCase, CheckoutModelPresenterMapper()) }

    private val userRepository by lazy { UserRepositoryImpl(LoginDataSource()) }

    private val transactionRepository by lazy { TransactionRepositoryImpl(placeToPlayService,
        CheckoutModelDataMapper()
    ) }

    private val getCurrentUserUseCase by lazy { GetCurrentUserUseCase(userRepository) }

    private val loginUseCase by lazy { LoginUseCase(userRepository) }

    private val sendCheckoutUseCase by lazy { SendCheckoutUseCase(getCurrentUserUseCase, transactionRepository) }

    private val retrofit by lazy {
        val gsonConverter = GsonConverterFactory.create()

        Retrofit.Builder()
            .baseUrl("https://dev.placetopay.com/rest/")
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    val placeToPlayService by lazy {
        retrofit.create(PlaceToPlayApiService::class.java)
    }
}