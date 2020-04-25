package com.example.paymentgateway.data.retrofit.util

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.data.retrofit.model.Amount
import com.example.paymentgateway.data.retrofit.model.Card
import com.example.paymentgateway.data.retrofit.model.CheckoutRequest
import com.example.paymentgateway.data.retrofit.model.Instrument
import com.example.paymentgateway.data.retrofit.model.Payment
import com.example.paymentgateway.data.retrofit.model.Person
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.entity.Transaction

class CheckoutRequestMapper : DataModelMapper<CheckoutRequest, Pair<LoggedInUser, Transaction>> {

    override fun map(entity: Pair<LoggedInUser, Transaction>): CheckoutRequest {
        val user = entity.first
        val trans = entity.second

        val auth = AuthenticationFactory(user).getAuthentication()
        val payment = Payment(
            trans.reference,
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