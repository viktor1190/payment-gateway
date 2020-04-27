package com.example.paymentgateway.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.paymentgateway.R
import com.example.paymentgateway.databinding.FragmentMenuBinding
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory
import com.example.paymentgateway.presentation.ui.login.LoginViewModel

class MenuFragment : Fragment() {

    // View Binding
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val loginViewModel by activityViewModels<LoginViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

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
            findNavController().navigate(R.id.action_menuFragment_to_paymentFormFragment)
        }
        binding.menuButtonPaymentList.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_transactionStatusListFragment)
        }
        binding.menuButtonLogout.setOnClickListener {
            loginViewModel.logout()
            findNavController().popBackStack(R.id.loginFragment, false)
        }
    }

}
