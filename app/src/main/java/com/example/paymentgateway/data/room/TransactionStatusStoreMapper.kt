package com.example.paymentgateway.data.room

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.core.toZonedDateTime
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import com.example.paymentgateway.data.room.entity.TransactionStatusStore

class TransactionStatusStoreMapper: DataModelMapper<TransactionStatusStore, StatusResponse> {

    override fun map(entity: StatusResponse): TransactionStatusStore {
        return TransactionStatusStore(
            entity.reference,
            entity.internalReference,
            entity.status.status,
            entity.amount.currency,
            entity.amount.total,
            entity.paymentMethod,
            entity.franchiseName,
            entity.authorization,
            entity.receipt,
            entity.status.date.toZonedDateTime()
        )
    }
}