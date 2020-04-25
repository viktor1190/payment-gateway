package com.example.paymentgateway.domain

import androidx.lifecycle.LiveData
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope

class SendCheckoutUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(coroutineScope: CoroutineScope, transaction: Transaction): LiveData<Resource<TransactionStatus>> {
        val userResource = getCurrentUserUseCase()
        return transactionRepository.sendCheckout(coroutineScope, userResource.data!!, transaction)
    }
}