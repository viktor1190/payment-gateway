package com.example.paymentgateway.data.core

interface DataModelMapper<out T, in I> {

    fun map(entity: I): T
}