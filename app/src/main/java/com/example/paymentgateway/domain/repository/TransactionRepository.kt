package com.example.paymentgateway.domain.repository

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus

interface TransactionRepository {

    suspend fun sendCheckout(loggedInUser: LoggedInUser, trasactionData: Transaction): TransactionStatus

    suspend fun getPaymentStatus(): TransactionStatus
}