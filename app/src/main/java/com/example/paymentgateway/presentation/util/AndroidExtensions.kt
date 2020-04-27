package com.example.paymentgateway.presentation.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.paymentgateway.R
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun Fragment.toast(message: String) {
    Toast.makeText(
        context,
        "$message",
        Toast.LENGTH_SHORT
    ).show()
}

fun CheckoutResultModel.getStatusDrawable(resources: Resources): Drawable {
    val icon = when (status) {
        "Approved" -> R.drawable.ic_check_24dp
        "Pending" -> R.drawable.ic_warning_24dp
        else -> R.drawable.ic_error_24dp
    }
    return resources.getDrawable(icon)
}

fun CheckoutResultModel.getStatusColor(resources: Resources): Int {
    val color = when (status) {
        "Approved" -> R.color.pass
        "Pending" -> R.color.warning
        else -> R.color.error
    }
    return resources.getColor(color)
}