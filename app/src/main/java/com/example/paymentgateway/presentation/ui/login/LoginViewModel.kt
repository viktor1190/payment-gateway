package com.example.paymentgateway.presentation.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymentgateway.R
import com.example.paymentgateway.domain.LoginUseCase
import com.example.paymentgateway.domain.LogoutUseCase
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.ui.login.state.LoggedInUserView
import com.example.paymentgateway.presentation.ui.login.state.LoginFormState
import com.example.paymentgateway.presentation.ui.login.state.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase, private val logoutUseCase: LogoutUseCase) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = loginUseCase(username, password)

            if (result is Resource.Success) {
                _loginResult.value =
                    LoginResult(
                        success = LoggedInUserView(
                            displayName = result.data!!.username
                        )
                    )
            } else {
                _loginResult.value =
                    LoginResult(
                        error = R.string.login_failed
                    )
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(
                    usernameError = R.string.invalid_username
                )
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(
                    passwordError = R.string.invalid_password
                )
        } else {
            _loginForm.value =
                LoginFormState(
                    isDataValid = true
                )
        }
    }

    fun logout() {
        logoutUseCase()
        _loginResult.postValue(LoginResult(null, R.string.logout_message))
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
