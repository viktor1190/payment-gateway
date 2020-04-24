package com.example.paymentgateway.data

import com.example.paymentgateway.domain.entity.Approved
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.TransactionRepository
import java.time.LocalDateTime

class TransactionRepositoryImpl: TransactionRepository {

    override suspend fun sendCheckout(loggedInUser: LoggedInUser, trasactionData: Transaction): TransactionStatus {
        print("user: $loggedInUser transactionData: $trasactionData")
        //TODO("Not yet implemented")
        return TransactionStatus(Approved(), LocalDateTime.now(), 123.toBigDecimal(), "fake", "fake", "", "", "","")
    }

    override suspend fun getPaymentStatus(): TransactionStatus {
        TODO("Not yet implemented")
    }
}