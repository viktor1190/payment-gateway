package com.example.paymentgateway.data.mapper

import com.example.paymentgateway.data.core.DataModelMapper
import com.example.paymentgateway.domain.entity.Status
import java.util.Locale

class StatusToDomainModelMapper: DataModelMapper<Array<String?>, Status> {

    override fun map(input: Array<String?>): Status {
        val status: String? = input.getOrNull(0)
        val reason: String? = input.getOrNull(1)
        val message: String? = input.getOrNull(2)
        return when (status) {
            Status.Approved::class.simpleName?.toUpperCase(Locale.ROOT) -> Status.Approved()
            Status.Pending::class.simpleName?.toUpperCase(Locale.ROOT) -> Status.Pending()
            Status.Failed::class.simpleName?.toUpperCase(Locale.ROOT) -> Status.Failed(reason, message)
            else -> Status.Rejected(reason, message)
        }
    }
}