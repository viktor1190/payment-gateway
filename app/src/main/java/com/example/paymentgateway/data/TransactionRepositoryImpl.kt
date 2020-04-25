package com.example.paymentgateway.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymentgateway.data.core.NetworkBoundResource
import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import com.example.paymentgateway.data.retrofit.util.ApiResponse
import com.example.paymentgateway.data.retrofit.util.CheckoutModelDataMapper
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

class TransactionRepositoryImpl(
    private val placeToPlayApiService: PlaceToPlayApiService,
    private val mapper: CheckoutModelDataMapper
): TransactionRepository {

    override suspend fun sendCheckout(coroutineScope: CoroutineScope, loggedInUser: LoggedInUser, trasactionData: Transaction): LiveData<Resource<TransactionStatus>> {
        return object : NetworkBoundResource<TransactionStatus, StatusResponse>(coroutineScope) {

            override suspend fun saveCallResult(item: StatusResponse) {
                Timber.d("Saving transaction to datastore: $item")
            }

            override fun shouldFetch(data: TransactionStatus?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<TransactionStatus> {
                return MutableLiveData(null) // TODO victor.valencia implement the data store
            }

            override fun createCall(): LiveData<ApiResponse<StatusResponse>> {
                return placeToPlayApiService.processTransaction(mapper.mapToDataModel(Pair(loggedInUser, trasactionData)))
            }
        }.asLiveData()
    }

    override suspend fun getPaymentStatus(): LiveData<Resource<TransactionStatus>> {
        TODO("Not yet implemented")
    }
}