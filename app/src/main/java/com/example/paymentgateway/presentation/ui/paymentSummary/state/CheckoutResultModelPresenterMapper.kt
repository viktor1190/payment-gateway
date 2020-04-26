package com.example.paymentgateway.presentation.ui.paymentSummary.state

import com.example.paymentgateway.data.core.toIsoDateString
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.presentation.core.PresentationModelMapper

class CheckoutResultModelPresenterMapper: PresentationModelMapper<CheckoutResultModel, TransactionStatus> {

    override fun mapToEntity(model: CheckoutResultModel): TransactionStatus {
        TODO("Not yet implemented")
    }

    override fun mapFromEntity(entity: TransactionStatus): CheckoutResultModel {
        return CheckoutResultModel(
            entity.reference,
            entity.internalReference.toString(),
            entity.status!!::class.simpleName ?: "(Unknow)",
            entity.status.reason,
            entity.status.message,
            entity.currency,
            entity.total,
            entity.franchiseName,
            entity.authorization,
            entity.receipt,
            entity.lastUpdate.toIsoDateString()
        )
    }
}