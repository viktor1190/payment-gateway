package com.example.paymentgateway.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.paymentgateway.data.core.NetworkBoundResource
import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import com.example.paymentgateway.data.retrofit.util.ApiResponse
import com.example.paymentgateway.data.retrofit.util.CheckoutRequestMapper
import com.example.paymentgateway.data.room.TransactionStatusDomainMapper
import com.example.paymentgateway.data.room.TransactionStatusStoreMapper
import com.example.paymentgateway.data.room.entity.TransactionStatusDao
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Status
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope

class TransactionRepositoryImpl(
    private val placeToPlayApiService: PlaceToPlayApiService,
    private val apiRequestMapper: CheckoutRequestMapper,
    private val transactionStatusDao: TransactionStatusDao,
    private val storeMapper: TransactionStatusStoreMapper,
    private val domainMapper: TransactionStatusDomainMapper
) : TransactionRepository {

    override suspend fun sendCheckout(
        coroutineScope: CoroutineScope,
        loggedInUser: LoggedInUser,
        transactionData: Transaction
    ): LiveData<Resource<TransactionStatus>> {
        return object : NetworkBoundResource<TransactionStatus, StatusResponse>(coroutineScope) {

            override suspend fun saveCallResult(item: StatusResponse) {
                transactionStatusDao.save(storeMapper.map(item))
            }

            override fun shouldFetch(data: TransactionStatus?): Boolean {
                return data == null || data.status !is Status.Approved
            }

            override fun loadFromDb(): LiveData<TransactionStatus> {
                return Transformations.map(transactionStatusDao.load(transactionData.reference)) { data ->
                    if (data != null) domainMapper.map(data) else null
                }
            }

            override fun createCall(): LiveData<ApiResponse<StatusResponse>> {
                return placeToPlayApiService.processTransaction(apiRequestMapper.map(Pair(loggedInUser, transactionData)))
            }
        }.asLiveData()
    }

    override suspend fun getPaymentStatusList(coroutineScope: CoroutineScope): LiveData<Resource<List<TransactionStatus?>>> {
        return object : NetworkBoundResource<List<TransactionStatus?>, StatusResponse>(coroutineScope) {

            override suspend fun saveCallResult(item: StatusResponse) {
                TODO("Not yet implemented")
            }

            override fun shouldFetch(data: List<TransactionStatus?>?): Boolean {
                return false // TODO victor.valencia this will avoid using the network API so far
            }

            override fun loadFromDb(): LiveData<List<TransactionStatus?>> {
                return Transformations.map(transactionStatusDao.listAll()) { list ->
                    list?.map { storedValue -> if (storedValue != null) domainMapper.map(storedValue) else null }
                        ?: emptyList<TransactionStatus>()
                }
            }

            override fun createCall(): LiveData<ApiResponse<StatusResponse>> {
                TODO("Not yet implemented")
            }

        }.asLiveData()
    }
}