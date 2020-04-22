package com.example.paymentgateway.presentation.ui.paymentForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.paymentgateway.databinding.FragmentPaymentFormBinding
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory

class PaymentFormFragment : Fragment() {

    // View Binding
    private var _binding: FragmentPaymentFormBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val viewModel by viewModels<PaymentFormViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentFormBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
