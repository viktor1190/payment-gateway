package com.example.paymentgateway.presentation.ui.paymentSummary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.paymentgateway.databinding.FragmentPaymentSummaryBinding
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultDecorator

class PaymentSummaryFragment : Fragment() {

    // View Binding
    private var _binding: FragmentPaymentSummaryBinding? = null
    private val binding get() = _binding!!
    val args: PaymentSummaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val checkoutResult = args.checkoutResult
        val checkoutHelper = CheckoutResultDecorator(checkoutResult)
        binding.textviewPyamentSummaryState.text = checkoutResult.status
        binding.textviewPyamentSummaryHeaders.text = checkoutHelper.getHeaders()
        binding.textviewPyamentSummaryValues.text = checkoutHelper.getValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
