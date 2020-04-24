package com.example.paymentgateway.domain

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): LoggedInUser {
        // TODO victor.valencia the user credentials are mocked here as far as there is not any
        //  requirement to fetch them from the API
        // TODO victor.valencia move this mock to the repo module
        return LoggedInUser("6dd490faf9cb87a9862245da41170ff2", "024h1IlD")
    }
}