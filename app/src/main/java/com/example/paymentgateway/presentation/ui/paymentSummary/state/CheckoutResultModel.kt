package com.example.paymentgateway.presentation.ui.paymentSummary.state

import android.os.Parcel
import android.os.Parcelable

data class CheckoutResultModel(
    val reference: String,
    val internalReference: String,
    val status: String,
    val statusReason: String?,
    val statusMessage: String?,
    val currency: String,
    val total: Float,
    val franchiseName: String?,
    val authorization: String?,
    val receipt: String,
    val lastUpdate: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reference)
        parcel.writeString(internalReference)
        parcel.writeString(status)
        parcel.writeString(statusReason)
        parcel.writeString(statusMessage)
        parcel.writeString(currency)
        parcel.writeFloat(total)
        parcel.writeString(franchiseName)
        parcel.writeString(authorization)
        parcel.writeString(receipt)
        parcel.writeString(lastUpdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckoutResultModel> {
        override fun createFromParcel(parcel: Parcel): CheckoutResultModel {
            return CheckoutResultModel(parcel)
        }

        override fun newArray(size: Int): Array<CheckoutResultModel?> {
            return arrayOfNulls(size)
        }
    }
}