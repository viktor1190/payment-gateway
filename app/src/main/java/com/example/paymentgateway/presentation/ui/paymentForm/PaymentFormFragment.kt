package com.example.paymentgateway.presentation.ui.paymentForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.paymentgateway.databinding.FragmentPaymentFormBinding
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory
import com.example.paymentgateway.presentation.util.afterTextChanged
import com.example.paymentgateway.presentation.util.toast
import timber.log.Timber

class PaymentFormFragment : Fragment() {

    // View Binding
    private var _binding: FragmentPaymentFormBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val viewModel by viewModels<PaymentFormViewModel> { factory }

    private val name: String
        get() = binding.textInputNewPaymentUserName.text.toString()

    private val email: String
        get() = binding.textInputNewPaymentUserEmail.text.toString()

    private val cellphone: String
        get() = binding.textInputNewPaymentUserCellphone.text.toString()

    private val cardNumber: String
        get() = binding.textInputNewPaymentCardNumber.text.toString()

    private val cardDueMonthAndYear: String
        get() = binding.textInputNewPaymentCardDueDate.text.toString()

    private val cardCvv: String
        get() = binding.textInputNewPaymentCardCvv2.text.toString()

    private val amount: String
        get() = binding.textInputNewPaymentPaymentAmount.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentFormBinding.inflate(inflater)
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
        viewModel.paymentFormState.observe(viewLifecycleOwner, Observer {
            val paymentState = it ?: return@Observer

            // disable submit button unless all input form data is valid
            binding.buttonSubmit.isEnabled = paymentState.isDataValid

            if (paymentState.nameError != null) {
                binding.textInputNewPaymentUserName.error = getString(paymentState.nameError)
            }
            if (paymentState.emailError != null) {
                binding.textInputNewPaymentUserEmail.error = getString(paymentState.emailError)
            }
            if (paymentState.cellphoneError != null) {
                binding.textInputNewPaymentUserCellphone.error = getString(paymentState.cellphoneError)
            }
            if (paymentState.cardNumberError != null) {
                binding.textInputNewPaymentCardNumber.error = getString(paymentState.cardNumberError)
            }
            if (paymentState.cardMonthAndYearError != null) {
                binding.textInputNewPaymentCardDueDate.error = getString(paymentState.cardMonthAndYearError)
            }
            if (paymentState.cardCvvError != null) {
                binding.textInputNewPaymentCardCvv2.error = getString(paymentState.cardCvvError)
            }
            if (paymentState.amountError != null) {
                binding.textInputNewPaymentPaymentAmount.error = getString(paymentState.amountError)
            }
        })

        viewModel.transactionResult.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    val transactionResult = resource.data
                    Timber.d("transaction status received: ${transactionResult}")
                    if (transactionResult != null) {
                        val mapper = ServiceLocator.checkoutResultModelPresenterMapper
                        val action = PaymentFormFragmentDirections.actionPaymentFormFragmentToPaymentSummaryFragment(
                            mapper.mapFromEntity(transactionResult)
                        )
                        findNavController().navigate(action)
                    }
                }
                is Resource.Loading -> Timber.d("LOADING payment transaction") // TODO victor.valencia show the progress dialog
                is Resource.Error -> {
                    toast("An error occurs: ${resource.message}")
                    Timber.e(resource.exception)
                }
            }
        })

        binding.textInputNewPaymentUserName.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentUserEmail.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentUserCellphone.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentCardNumber.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentCardDueDate.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentCardCvv2.afterTextChanged { notifyDataChanged() }
        binding.textInputNewPaymentPaymentAmount.afterTextChanged { notifyDataChanged() }

        binding.buttonSubmit.setOnClickListener {
            viewModel.submit(name, email, cellphone, cardNumber, cardDueMonthAndYear, cardCvv, amount)
        }
        binding.buttonSubmit.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    viewModel.submit(name, email, cellphone, cardNumber, cardDueMonthAndYear, cardCvv, amount)
            }
            false
        }
    }

    private fun notifyDataChanged() {
        viewModel.paymentDataChanged(name, email, cellphone, cardNumber, cardDueMonthAndYear, cardCvv, amount)
    }
}
