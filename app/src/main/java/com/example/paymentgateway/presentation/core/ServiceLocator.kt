package com.example.paymentgateway.presentation.core

import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceLocator {

    val viewModelFactory by lazy { ViewModelFactory() }

    private val retrofit by lazy {
        val gsonConverter = GsonConverterFactory.create()

        Retrofit.Builder()
            .baseUrl("https://dev.placetopay.com/rest/")
            .addConverterFactory(gsonConverter)
            .build()
    }

    val placeToPlayService by lazy {
        retrofit.create(PlaceToPlayApiService::class.java)
    }
}