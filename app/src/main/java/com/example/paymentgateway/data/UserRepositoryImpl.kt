package com.example.paymentgateway.data

import com.example.paymentgateway.domain.entity.LoggedInUser
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.domain.repository.UserRepository

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class UserRepositoryImpl(val dataSource: LoginDataSource): UserRepository {

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

    override suspend fun logout(): Resource<Nothing?> {
        user = null
        dataSource.logout()
        return Resource.Success(null)
    }

    override suspend fun loadUser(): Resource<LoggedInUser> {
        // TODO victor.valencia the user credentials are mocked here as far as there is not any
        //  requirement to fetch them from the API
        return Resource.Success(LoggedInUser("6dd490faf9cb87a9862245da41170ff2", "024h1IlD"))
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}