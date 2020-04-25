package com.example.paymentgateway.data.core

interface DataModelMapper<M, E> {

    fun mapToDataModel(entity: E): M
}