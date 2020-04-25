package com.example.paymentgateway.data.room

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.room.entity.TransactionStatusStore
import com.example.paymentgateway.domain.entity.Status
import com.example.paymentgateway.domain.entity.TransactionStatus

class TransactionStatusDomainMapper : DataModelMapper<TransactionStatus?, TransactionStatusStore?> {

    override fun map(entity: TransactionStatusStore?): TransactionStatus? {
        if (entity == null) return null
        val status = when (entity.status) {
            Status.Approved::class.simpleName -> Status.Approved()
            Status.Pending::class.simpleName -> Status.Pending()
            Status.Rejected::class.simpleName -> Status.Rejected("", "") // TODO victor. valencia serialize this
            else -> Status.Failed("", "")
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