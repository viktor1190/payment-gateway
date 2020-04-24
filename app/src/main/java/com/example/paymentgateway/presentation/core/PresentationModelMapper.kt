package com.example.paymentgateway.presentation.core

interface PresentationModelMapper<in M, out E> {

    fun mapToEntity(model: M): E
}