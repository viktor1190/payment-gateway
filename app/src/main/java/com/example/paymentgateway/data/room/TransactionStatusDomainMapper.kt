package com.example.paymentgateway.data.room

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.room.entity.TransactionStatusStore
import com.example.paymentgateway.domain.entity.Status
import com.example.paymentgateway.domain.entity.TransactionStatus

class TransactionStatusDomainMapper : DataModelMapper<TransactionStatus?, TransactionStatusStore?> {

    override fun map(entity: TransactionStatusStore?): TransactionStatus? {
        if (entity == null) return null
        val status = when (entity.status) {
            Status.Approved::class.simpleName?.toUpperCase() -> Status.Approved()
            Status.Pending::class.simpleName?.toUpperCase() -> Status.Pending()
            Status.Failed::class.simpleName?.toUpperCase() -> Status.Failed(entity.reason, entity.message)
            else -> Status.Rejected(entity.reason, entity.message)
        }
        return TransactionStatus(
            entity.reference,
            entity.internalReference,
            status,
            entity.currency,
            entity.total,
            entity.franchiseName,
            entity.authorization,
            entity.receipt,
            entity.lastUpdate
        )
    }
}