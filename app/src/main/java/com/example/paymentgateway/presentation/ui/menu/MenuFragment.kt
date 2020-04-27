package com.example.paymentgateway.presentation.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.paymentgateway.R
import com.example.paymentgateway.databinding.FragmentMenuBinding
import com.example.paymentgateway.presentation.core.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuFragment : Fragment() {

    // View Binding
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val logoutUseCase = ServiceLocator.logoutUseCase

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
            GlobalScope.launch {
                withContext(Dispatchers.Main) { logoutUseCase() }
                val action = MenuFragmentDirections.actionMenuFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

}
