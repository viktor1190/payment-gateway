package com.example.paymentgateway.domain

import com.example.paymentgateway.domain.repository.TransactionRepository

class DeleteTransactionUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(reference: String) {
        transactionRepository.deleteLocalTransaction(reference)
    }
}