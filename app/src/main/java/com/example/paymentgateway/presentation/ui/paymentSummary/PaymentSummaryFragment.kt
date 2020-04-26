package com.example.paymentgateway.presentation.ui.paymentSummary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.paymentgateway.databinding.FragmentPaymentSummaryBinding
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory

class PaymentSummaryFragment : Fragment() {

    // View Binding
    private var _binding: FragmentPaymentSummaryBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val viewModel by viewModels<PaymentSummaryViewModel> { factory }
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
        binding.textviewPyamentSummaryState.text = checkoutResult.status
        binding.textviewPyamentSummaryHeaders.text = checkoutResult.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
