package com.example.paymentgateway.data.retrofit.util

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.core.generateRandomReference
import com.example.paymentgateway.data.retrofit.model.*
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction

class CheckoutModelDataMapper : DataModelMapper<CheckoutRequest, Pair<LoggedInUser, Transaction>> {

    override fun mapToDataModel(entity: Pair<LoggedInUser, Transaction>): CheckoutRequest {
        val user = entity.first
        val trans = entity.second

        val auth = AuthenticationFactory(user).getAuthentication()
        val payment = Payment(
            trans.reference ?: generateRandomReference(),
            Amount(trans.currency, trans.total)
        )
        val instrument = Instrument(
            Card(
                trans.cardNumber,
                trans.cardExpirationMonth,
                trans.cardExpirationYear,
                trans.cardCvv
            )
        )
        val payer = Person(trans.payerName, trans.payerEmail, trans.payerMobile)

        return CheckoutRequest(auth, payment, instrument, payer)
    }
}