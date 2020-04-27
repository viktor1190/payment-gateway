package com.example.paymentgateway.data.core

interface DataModelMapper<in I, out T> {

    fun map(model: I): T
}