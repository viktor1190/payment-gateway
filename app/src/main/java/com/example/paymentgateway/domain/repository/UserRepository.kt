package com.example.paymentgateway.domain.repository

import com.example.paymentgateway.domain.entity.LoggedInUser

interface UserRepository {

    suspend fun login(userName: String, userPassword: String): Resource<LoggedInUser>

    fun loadUser(): Resource<LoggedInUser>

    fun logout(): Resource<Nothing?>
}