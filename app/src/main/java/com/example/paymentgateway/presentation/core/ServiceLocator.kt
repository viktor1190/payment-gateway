package com.example.paymentgateway.presentation.core

import androidx.room.Room
import com.example.paymentgateway.data.LoginDataSource
import com.example.paymentgateway.data.TransactionRepositoryImpl
import com.example.paymentgateway.data.UserRepositoryImpl
import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import com.example.paymentgateway.data.retrofit.util.CheckoutRequestMapper
import com.example.paymentgateway.data.retrofit.util.LiveDataCallAdapterFactory
import com.example.paymentgateway.data.room.Database
import com.example.paymentgateway.data.room.TransactionStatusDomainMapper
import com.example.paymentgateway.data.room.TransactionStatusStoreMapper
import com.example.paymentgateway.domain.GetCurrentUserUseCase
import com.example.paymentgateway.domain.GetTransactionStatusListUseCase
import com.example.paymentgateway.domain.LoginUseCase
import com.example.paymentgateway.domain.SendCheckoutUseCase
import com.example.paymentgateway.presentation.ApplicationController
import com.example.paymentgateway.presentation.ui.paymentForm.state.CheckoutModelPresenterMapper
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModelPresenterMapper
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceLocator {

    // APP context
    lateinit var appContext: ApplicationController

    // Direct Injectors
    val viewModelFactory by lazy {
        ViewModelFactory(
            loginUseCase,
            sendCheckoutUseCase,
            getTransactionStatusListUseCase,
            CheckoutModelPresenterMapper(),
            CheckoutResultModelPresenterMapper()
        )
    }
    val checkoutResultModelPresenterMapper: CheckoutResultModelPresenterMapper by lazy { CheckoutResultModelPresenterMapper() }

    // Use Cases
    private val getCurrentUserUseCase by lazy { GetCurrentUserUseCase(userRepository) }
    private val loginUseCase by lazy { LoginUseCase(userRepository) }
    private val sendCheckoutUseCase by lazy { SendCheckoutUseCase(getCurrentUserUseCase, transactionRepository) }
    private val getTransactionStatusListUseCase by lazy { GetTransactionStatusListUseCase(transactionRepository) }

    // Repositories
    private val userRepository by lazy { UserRepositoryImpl(LoginDataSource()) }
    private val transactionRepository by lazy {
        TransactionRepositoryImpl(
            placeToPlayService,
            CheckoutRequestMapper(),
            database.transactionStatusDao(),
            TransactionStatusStoreMapper(),
            TransactionStatusDomainMapper()
        )
    }

    // Services
    private val retrofit by lazy {
        val gsonConverter = GsonConverterFactory.create()

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl("https://dev.placetopay.com/rest/")
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }
    private val placeToPlayService by lazy {
        retrofit.create(PlaceToPlayApiService::class.java)
    }
    private val database by lazy {
        Room.databaseBuilder(appContext, Database::class.java, "paymentLocalStore.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}