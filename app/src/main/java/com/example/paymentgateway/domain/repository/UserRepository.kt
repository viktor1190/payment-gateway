package com.example.paymentgateway.domain.repository

interface UserRepository {

    suspend fun saveUser(userName: String, userPassword: String)

    suspend fun loadUser()
}