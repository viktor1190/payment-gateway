package com.example.paymentgateway.domain.entity

import java.math.BigDecimal
import java.time.ZonedDateTime

sealed class Status {
    class Approved: Status()
    class Pending: Status()
    open class Failed(val reason: String, val message: String): Status()
    class Rejected(reason: String, message: String) : Failed(reason, message)
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