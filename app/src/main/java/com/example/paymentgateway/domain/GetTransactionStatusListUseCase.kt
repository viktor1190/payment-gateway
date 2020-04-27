package com.example.paymentgateway.domain

import androidx.lifecycle.LiveData
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope

class GetTransactionStatusListUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(coroutineScope: CoroutineScope): LiveData<Resource<List<TransactionStatus?>>> {
        val userResource = getCurrentUserUseCase()
        return transactionRepository.getPaymentStatusList(coroutineScope, userResource.data!!)
    }
}