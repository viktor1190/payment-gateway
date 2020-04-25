package com.example.paymentgateway.domain.repository

/**
 * A generic class that contains data and status values about its loading status.
 * @param <T>
 */

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(val exception: Exception, message: String? = null, data: T? = null) : Resource<T>(data, message)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Loading -> "Loading[]"
            is Error -> "Error[exception=$exception, message=$message]"
        }
    }
}
