package com.example.paymentgateway.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.paymentgateway.data.core.NetworkBoundResource
import com.example.paymentgateway.data.mapper.CheckoutRequestToTransactionDomainModelMapper
import com.example.paymentgateway.data.mapper.TransactionStatusResponseToDomainMapper
import com.example.paymentgateway.data.mapper.TransactionStatusResponseToStoreMapper
import com.example.paymentgateway.data.mapper.TransactionStatusToStoreMapper
import com.example.paymentgateway.data.retrofit.PlaceToPlayApiService
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import com.example.paymentgateway.data.retrofit.util.ApiResponse
import com.example.paymentgateway.data.room.entity.TransactionStatusDao
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Status
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(
    private val placeToPlayApiService: PlaceToPlayApiService,
    private val transactionStatusDao: TransactionStatusDao,
    private val transactionRequestToDomainMapper: CheckoutRequestToTransactionDomainModelMapper,
    private val transactionResponseToStoreMapper: TransactionStatusResponseToStoreMapper,
    private val mapper: TransactionStatusResponseToDomainMapper,
    private val transactionToStoreMapper: TransactionStatusToStoreMapper
) : TransactionRepository {

    override suspend fun sendCheckout(
        coroutineScope: CoroutineScope,
        loggedInUser: LoggedInUser,
        transactionData: Transaction
    ): LiveData<Resource<TransactionStatus>> {
        return object : NetworkBoundResource<TransactionStatus, StatusResponse>(coroutineScope) {

            override suspend fun saveCallResult(item: StatusResponse) {
                transactionStatusDao.save(transactionResponseToStoreMapper.map(item))
            }

            override fun shouldFetch(data: TransactionStatus?): Int {
                return if (data == null) 1 else 0
            }

            override fun loadFromDb(): LiveData<TransactionStatus> {
                return Transformations.map(transactionStatusDao.load(transactionData.reference)) { data ->
                    if (data != null) transactionToStoreMapper.map(data) else null
                }
            }

            override fun createCall(): LiveData<ApiResponse<StatusResponse>> {
                return placeToPlayApiService.processTransaction(transactionRequestToDomainMapper.map(Pair(loggedInUser, transactionData)))
            }
        }.asLiveData()
    }

    override suspend fun getPaymentStatusList(
        coroutineScope: CoroutineScope,
        loggedInUser: LoggedInUser
    ): LiveData<Resource<List<TransactionStatus?>>> {
        return object : NetworkBoundResource<List<TransactionStatus?>, StatusResponse>(coroutineScope) {

            private var pendingTransactionsList: MutableList<TransactionStatus> = mutableListOf()

            override suspend fun saveCallResult(item: StatusResponse) {
                transactionStatusDao.save(transactionResponseToStoreMapper.map(item))
            }

            override fun shouldFetch(data: List<TransactionStatus?>?): Int {
                data?.let {
                    val pendingStatus: List<TransactionStatus> = data.requireNoNulls().filter { it.status is Status.Pending }
                    pendingTransactionsList.addAll(pendingStatus)
                }
                return pendingTransactionsList.size
            }

            override fun loadFromDb(): LiveData<List<TransactionStatus?>> {
                return Transformations.map(transactionStatusDao.listAll()) { list ->
                    list?.map { storedValue -> if (storedValue != null) transactionToStoreMapper.map(storedValue) else null }
                        ?: emptyList<TransactionStatus>()
                }
            }

            override fun createCall(): LiveData<ApiResponse<StatusResponse>> {
                // In order to update multiple transaction at once, we need to merge multiple API calls into one LiveData object:
                val mediator = MediatorLiveData<ApiResponse<StatusResponse>>()
                pendingTransactionsList.forEach { transactionStatus ->
                    val request = mapper.map(Pair(loggedInUser, transactionStatus))
                    if (request != null) {
                        mediator.addSource(placeToPlayApiService.getTransactionStatus(request)) { apiResponse ->
                            mediator.value = apiResponse
                        }
                    }
                }
                return mediator
            }

        }.asLiveData()
    }

    override suspend fun deleteLocalTransaction(reference: String) {
        withContext(Dispatchers.IO) {
            transactionStatusDao.deleteById(reference)
        }
    }
}