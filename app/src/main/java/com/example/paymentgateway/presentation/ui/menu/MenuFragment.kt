package com.example.paymentgateway.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.paymentgateway.databinding.FragmentMenuBinding
import com.example.paymentgateway.presentation.util.toast

class MenuFragment : Fragment() {

    // View Binding
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        binding.menuButtonNewPayment.setOnClickListener {
            toast("Go to generate new Payment")
        }
        binding.menuButtonPaymentList.setOnClickListener {
            toast("Go to see the list of Payments")
        }
    }

}
