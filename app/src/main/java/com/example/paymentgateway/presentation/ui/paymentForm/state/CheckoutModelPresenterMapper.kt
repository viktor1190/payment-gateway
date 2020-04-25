package com.example.paymentgateway.presentation.ui.paymentForm.state

import com.example.paymentgateway.domain.entity.Transaction
import com.example.paymentgateway.presentation.core.PresentationModelMapper
import com.example.paymentgateway.presentation.ui.paymentForm.CARD_DUE_MONTH_AND_YEAR_PATTERN

class CheckoutModelPresenterMapper: PresentationModelMapper<CheckoutModel, Transaction> {

    override fun mapToEntity(model: CheckoutModel): Transaction {
        val cardDueDateMatch = CARD_DUE_MONTH_AND_YEAR_PATTERN.toRegex().find(model.cardDueMonthAndYear) ?: throw UnknownError()
        return Transaction(
            currency = model.amount.currency.currencyCode,
            total = model.amount.number.toFloat(),
            cardNumber = model.cardNumber,
            cardExpirationMonth = cardDueDateMatch.groups[1]?.value ?: throw UnknownError(),
            cardExpirationYear = cardDueDateMatch.groups[2]?.value ?:  throw UnknownError(),
            cardCvv = model.cardCvv,
            payerName = model.name,
            payerEmail = model.email,
            payerMobile = model.cellphone
        )
    }
}