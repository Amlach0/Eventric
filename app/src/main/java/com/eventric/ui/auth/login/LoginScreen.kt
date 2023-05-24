package com.eventric.ui.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.eventric.BuildConfig
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.SuccessOperation
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    signin: () -> Unit,
    onSuccess: () -> Unit
) {

    val loginState by loginViewModel.loginCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf(BuildConfig.USERNAME) }
    var password by remember { mutableStateOf(BuildConfig.PASSWORD) }

    var passwordVisible by remember { mutableStateOf(false) }

    var errorBannerIsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is ErrorOperation) errorBannerIsVisible = true
        if (loginState is SuccessOperation) onSuccess()
    }

    LaunchedEffect(email, password) {
        errorBannerIsVisible = false
    }

    fun onPasswordVisibleChange(value: Boolean) {
        passwordVisible = value
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onSubmit() = coroutineScope.launch {
        if (loginState !is LoadingOperation) {
            try {
                loginViewModel.login(email, password)
            } catch (e: Exception) {
                // Nothing to do
            }
        }
    }

    fun onSigninPressed(){
        signin()
    }

    EventricTheme {
        LoginContent(
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            errorBannerIsVisible = errorBannerIsVisible,
            onPasswordVisibleChange = ::onPasswordVisibleChange,
            onEmailChange = ::onEmailChange,
            onPasswordChange = ::onPasswordChange,
            onSubmit = ::onSubmit,
            onSigninPressed = ::onSigninPressed,
        )
    }


}