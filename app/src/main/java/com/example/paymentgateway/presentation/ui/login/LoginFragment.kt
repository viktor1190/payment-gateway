package com.example.paymentgateway.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.paymentgateway.R
import com.example.paymentgateway.databinding.FragmentLoginBinding
import com.example.paymentgateway.presentation.ui.login.core.LoginViewModelFactory
import com.example.paymentgateway.presentation.ui.login.state.LoggedInUserView
import com.example.paymentgateway.presentation.util.afterTextChanged
import com.example.paymentgateway.presentation.util.toast

class LoginFragment: Fragment() {

    // View Binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val factory: LoginViewModelFactory =
        LoginViewModelFactory()
    private val loginViewModel by viewModels<LoginViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
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
        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            // TODO: victor.valencia setResult(Activity.RESULT_OK) and launch the next activity
        })

        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            binding.username.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }

            binding.login.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
            }
        }
    }



    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        toast("$welcome $displayName")
        // initiate successful logged in experience
        findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        toast(getString(errorString))
    }
}