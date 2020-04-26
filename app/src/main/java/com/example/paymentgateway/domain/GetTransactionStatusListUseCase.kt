package com.example.paymentgateway.domain

import androidx.lifecycle.LiveData
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope

class GetTransactionStatusListUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(coroutineScope: CoroutineScope): LiveData<Resource<List<TransactionStatus?>>> {
        return transactionRepository.getPaymentStatusList(coroutineScope)
    }
}