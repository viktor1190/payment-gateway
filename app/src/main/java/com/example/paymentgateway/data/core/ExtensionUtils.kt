package com.example.paymentgateway.data.core

import com.example.paymentgateway.data.retrofit.util.CheckoutModelDataMapper
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun CheckoutModelDataMapper.generateRandomReference(): String {
    return "TEST-" + ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}