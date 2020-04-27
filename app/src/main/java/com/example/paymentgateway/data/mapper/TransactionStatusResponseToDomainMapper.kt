package com.example.paymentgateway.data.mapper

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.retrofit.model.StatusRequest
import com.example.paymentgateway.data.retrofit.util.AuthenticationFactory
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.TransactionStatus

class TransactionStatusResponseToDomainMapper: DataModelMapper<Pair<LoggedInUser, TransactionStatus>, StatusRequest?> {

    override fun map(model: Pair<LoggedInUser, TransactionStatus>): StatusRequest? {
        val tranStatus = model.second
        return if (tranStatus.internalReference != null) {
            val user = model.first
            val auth = AuthenticationFactory(user).getAuthentication()
            return StatusRequest(auth, tranStatus.internalReference.toBigInteger())
        } else null
    }
}