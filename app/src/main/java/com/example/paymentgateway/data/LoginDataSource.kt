package com.example.paymentgateway.data

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.Resource
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Resource<LoggedInUser> {
        try {
            // TODO: victor.valencia handle loggedInUser authentication
            val fakeUser = LoggedInUser(username, password)
            return Resource.Success(fakeUser)
        } catch (e: Throwable) {
            return Resource.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: victor.valencia revoke authentication
    }
}

