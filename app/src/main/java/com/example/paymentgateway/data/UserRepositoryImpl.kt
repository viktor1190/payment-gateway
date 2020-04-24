package com.example.paymentgateway.data

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.UserRepository

class UserRepositoryImpl: UserRepository {

    override suspend fun saveUser(userName: String, userPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadUser(): LoggedInUser {
        // TODO victor.valencia the user credentials are mocked here as far as there is not any
        //  requirement to fetch them from the API
        return LoggedInUser("6dd490faf9cb87a9862245da41170ff2", "024h1IlD")
    }
}