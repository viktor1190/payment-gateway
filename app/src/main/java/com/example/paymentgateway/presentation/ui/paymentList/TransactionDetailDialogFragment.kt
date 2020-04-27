package com.example.paymentgateway.presentation.ui.paymentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.paymentgateway.R
import com.example.paymentgateway.databinding.FragmentTransactionDetailDialogBinding
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultDecorator
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import com.example.paymentgateway.presentation.util.getStatusColor
import com.example.paymentgateway.presentation.util.getStatusDrawable

const val ARG_CHECKOUT_MODEL = "checkout_model"

class TransactionDetailDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentTransactionDetailDialogBinding

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val viewModel by viewModels<TransactionStatusListViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.dialog?.setTitle(R.string.transactionDetailDialog_dialogTitle)
        val checkout = arguments?.getParcelable<CheckoutResultModel>(ARG_CHECKOUT_MODEL)!!
        val statusDrawable = checkout.getStatusDrawable(resources)
        val statusColor = checkout.getStatusColor(resources)
        val checkoutHelper = CheckoutResultDecorator(checkout)
        binding.textViewTransactionDetailDialogStatus.text = checkout.status
        binding.textViewTransactionDetailDialogStatus.setCompoundDrawablesWithIntrinsicBounds(null, null, null, statusDrawable)
        binding.textViewTransactionDetailDialogStatus.setBackgroundColor(statusColor)
        binding.textviewPyamentSummaryHeaders.text = checkoutHelper.getHeaders()
        binding.textviewPyamentSummaryValues.text = checkoutHelper.getValues()
        binding.buttonTransactionDetailDialogDelete.setOnClickListener {
            viewModel.deleteTransaction(checkout.reference)
            dismiss()
        }
        binding.buttonTransactionDetailDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "TransactionDetailDialogFragment.TAG"

        fun newInstance(checkoutResultModel: CheckoutResultModel): TransactionDetailDialogFragment =
            TransactionDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHECKOUT_MODEL, checkoutResultModel)
                }
            }

    }
}
