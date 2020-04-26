package com.example.paymentgateway.domain.repository

import androidx.lifecycle.LiveData
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.domain.entity.TransactionStatus
import kotlinx.coroutines.CoroutineScope

interface TransactionRepository {

    suspend fun sendCheckout(coroutineScope: CoroutineScope, loggedInUser: LoggedInUser, transactionData: Transaction): LiveData<Resource<TransactionStatus>>

    suspend fun getPaymentStatusList(coroutineScope: CoroutineScope): LiveData<Resource<List<TransactionStatus?>>>
}