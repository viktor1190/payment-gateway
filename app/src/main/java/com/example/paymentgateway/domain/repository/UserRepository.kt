package com.example.paymentgateway.domain.repository

import com.example.paymentgateway.domain.entity.LoggedInUser

interface UserRepository {

    suspend fun login(userName: String, userPassword: String): Resource<LoggedInUser>

    suspend fun loadUser(): Resource<LoggedInUser>

    suspend fun logout(): Resource<Nothing?>
}