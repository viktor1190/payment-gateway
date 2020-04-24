package com.example.paymentgateway.domain

import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.TransactionRepository

class SendCheckoutUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(transaction: Transaction): TransactionStatus {
        val user = getCurrentUserUseCase()
        return transactionRepository.sendCheckout(user, transaction)
    }
}