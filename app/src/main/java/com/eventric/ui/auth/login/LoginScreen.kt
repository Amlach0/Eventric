package com.eventric.ui.auth.login

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.eventric.BuildConfig
import com.eventric.repo.ErrorOperation
import com.eventric.repo.LoadingOperation
import com.eventric.repo.SuccessOperation
import com.eventric.ui.theme.EventricTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {

    val loginState by loginViewModel.loginCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

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
        )
    }


}