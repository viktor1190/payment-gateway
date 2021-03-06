package com.example.paymentgateway.data.core

/*
 * This class is based in the implementation of GithubBrowserSample project created by Google at
 * https://github.com/android/architecture-components-samples/blob/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
 *
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.paymentgateway.data.retrofit.util.ApiErrorResponse
import com.example.paymentgateway.data.retrofit.util.ApiResponse
import com.example.paymentgateway.data.retrofit.util.ApiSuccessResponse
import com.example.paymentgateway.domain.repository.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val coroutineScope: CoroutineScope) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.Loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            val amountOfDataToFetch = shouldFetch(data)
            if (amountOfDataToFetch > 0) {
                fetchFromNetwork(dbSource, amountOfDataToFetch)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>, amountOfDataToFetch: Int) {
        var pendingDataAmount = amountOfDataToFetch
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }
        result.addSource(apiResponse) { response ->
            if (pendingDataAmount >= 1) {
                pendingDataAmount = 0
                result.removeSource(apiResponse)
            }
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    coroutineScope.launch(Dispatchers.IO) {
                        saveCallResult(processResponse(response))
                        if (pendingDataAmount == 0) {
                            withContext(Dispatchers.Main) {
                                // we specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                result.addSource(loadFromDb()) { newData ->
                                    setValue(Resource.Success(newData))
                                }
                            }
                        }
                    }
                }
                is ApiErrorResponse -> {
                    pendingDataAmount = 0
                    onFetchFailed()
                    result.removeSource(apiResponse)
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.Error(NetworkException(response.errorMessage), response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open suspend fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Int

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}