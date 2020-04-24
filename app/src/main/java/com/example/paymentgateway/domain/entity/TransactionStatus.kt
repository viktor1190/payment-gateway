package com.example.paymentgateway.domain.entity

import java.math.BigDecimal
import java.time.LocalDateTime

sealed class Status()

class Approved: Status()
class Pending: Status()
open class Failed(val reason: String, val message: String): Status()
class Rejected(reason: String, message: String) : Failed(reason, message)

data class TransactionStatus(
    val status: Status,
    val date: LocalDateTime,
    val internalReference: BigDecimal,
    val reference: String,
    val franchise: String,
    val amount: String,
    val currency: String,
    val authorization: String,
    val receipt: String
)