package com.example.paymentgateway.domain

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Resource<LoggedInUser> {
        return userRepository.loadUser()
    }
}