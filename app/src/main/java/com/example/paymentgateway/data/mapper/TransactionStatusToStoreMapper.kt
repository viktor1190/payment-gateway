package com.example.paymentgateway.data.mapper

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.room.entity.TransactionStatusStore
import com.example.paymentgateway.domain.entity.TransactionStatus

class TransactionStatusToStoreMapper(private val statusToDomainModelMapper: StatusToDomainModelMapper) :
    DataModelMapper<TransactionStatusStore, TransactionStatus> {

    override fun map(entity: TransactionStatusStore): TransactionStatus {

        return TransactionStatus(
            entity.reference,
            entity.internalReference,
            statusToDomainModelMapper.map(arrayOf(entity.status, entity.reason, entity.message)),
            entity.currency,
            entity.total,
            entity.franchiseName,
            entity.authorization,
            entity.receipt,
            entity.lastUpdate
        )
    }
}