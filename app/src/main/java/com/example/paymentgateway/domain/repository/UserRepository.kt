package com.example.paymentgateway.domain.repository

import com.example.paymentgateway.domain.entity.LoggedInUser

interface UserRepository {

    suspend fun saveUser(userName: String, userPassword: String)

    suspend fun loadUser(): LoggedInUser
}