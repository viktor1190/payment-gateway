package com.example.paymentgateway.data.retrofit

import androidx.lifecycle.LiveData
import com.example.paymentgateway.data.retrofit.model.CheckoutRequest
import com.example.paymentgateway.data.retrofit.model.StatusRequest
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import com.example.paymentgateway.data.retrofit.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This interface was designed according to the PlaceToPay API service definition
 */
interface PlaceToPlayApiService {

    @POST("gateway/process")
    fun processTransaction(
        @Body request: CheckoutRequest
    ): LiveData<ApiResponse<StatusResponse>>

    @POST(" gateway/query")
    fun getTransactionStatus(
        @Body request: StatusRequest
    ): LiveData<ApiResponse<StatusResponse>>

}