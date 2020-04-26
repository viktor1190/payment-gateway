package com.example.paymentgateway.presentation.core

interface PresentationModelMapper<M, E> {

    fun mapToEntity(model: M): E

    fun mapFromEntity(entity: E): M
}