package com.example.paymentgateway.presentation.ui.paymentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentgateway.domain.GetTransactionStatusListUseCase
import com.example.paymentgateway.domain.entity.TransactionStatus
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModelPresenterMapper
import kotlinx.coroutines.launch

class TransactionStatusListViewModel(
    private val getTransactionStatusListUseCase: GetTransactionStatusListUseCase,
    private val checkoutMapper: CheckoutResultModelPresenterMapper
) : ViewModel() {

    lateinit var transactionListLiveData: LiveData<Resource<List<CheckoutResultModel?>>>

    init {
        fetchTransactionStatusList()
    }

    private fun fetchTransactionStatusList() {
        viewModelScope.launch {
            transactionListLiveData =
                Transformations.map(getTransactionStatusListUseCase(this)) { resource: Resource<List<TransactionStatus?>> ->
                    when (resource) {
                        is Resource.Success -> Resource.Success<List<CheckoutResultModel?>>(resource.data?.map { transactionStatus ->
                            if (transactionStatus != null) {
                                checkoutMapper.mapFromEntity(transactionStatus)
                            } else null
                        } ?: emptyList())
                        is Resource.Error -> Resource.Error<List<CheckoutResultModel?>>(
                            resource.exception,
                            resource.message,
                            emptyList<CheckoutResultModel>()
                        )
                        else -> Resource.Loading<List<CheckoutResultModel?>>(emptyList())
                    }
                }
        }
    }
}