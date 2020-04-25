package com.example.paymentgateway.data.core

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun generateRandomReference(): String {
    return "TEST-" + ZonedDateTime.now().toIsoDateString()
}

fun String.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

fun ZonedDateTime.toIsoDateString(): String {
    return truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}