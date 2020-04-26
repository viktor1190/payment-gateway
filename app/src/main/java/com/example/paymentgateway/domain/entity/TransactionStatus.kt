package com.example.paymentgateway.domain.entity

import java.math.BigDecimal
import java.time.ZonedDateTime

sealed class Status(val reason: String?, val message: String?) {
    class Approved: Status(null, null)
    class Pending: Status(null, null)
    class Failed(reason: String?, message: String?): Status(reason, message)
    class Rejected(reason: String?, message: String?) : Status(reason, message)
}

data class TransactionStatus(
    val reference: String,
    val internalReference: BigDecimal?,
    val status: Status?,
    val currency: String,
    val total: Float,
    val franchiseName: String?,
    val authorization: String?,
    val receipt: String,
    val lastUpdate: ZonedDateTime
)