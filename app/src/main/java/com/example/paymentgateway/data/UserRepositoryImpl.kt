package com.example.paymentgateway.data

import com.example.paymentgateway.data.core.UserNoLoggedInException
import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.UserRepository

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class UserRepositoryImpl(val dataSource: LoginDataSource): UserRepository {

    // TODO victor.valencia the user credentials are mocked here as far as there is not any
    //  requirement to fetch them from the API
    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    override suspend fun login(userName: String, userPassword: String): Resource<LoggedInUser> {
        // handle login
        val result = dataSource.login(userName, userPassword)

        if (result is Resource.Success && result.data != null) {
            setLoggedInUser(result.data)
        }

        return result
    }

    override fun logout(): Resource<Nothing?> {
        user = null
        dataSource.logout()
        return Resource.Success(null)
    }

    override fun loadUser(): Resource<LoggedInUser> {
        val currentUser = user
        return if (currentUser != null) Resource.Success(currentUser) else Resource.Error(UserNoLoggedInException("There isn't logged in user"))
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}