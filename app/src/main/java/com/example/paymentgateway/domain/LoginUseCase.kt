package com.example.paymentgateway.domain

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String, password: String): Resource<LoggedInUser> {
        return userRepository.login(username, password)
    }
}

class LogoutUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        userRepository.logout()
    }
}