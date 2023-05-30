package com.eventric.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.LoadingOperation
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel(),
) {
    val signupState by signupViewModel.signupCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var errorBannerIsVisible by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var birthDate by remember { mutableStateOf("") }

    fun onNameChange(value: String) {
        name = value
    }

    fun onSurnameChange(value: String) {
        surname = value
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordVisibleChange(value: Boolean) {
        passwordVisible = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onConfirmPasswordVisibleChange(value: Boolean) {
        confirmPasswordVisible = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
    }

    fun onBirthDateSelected(value: String){
        birthDate = value
    }

    fun onSubmit() = coroutineScope.launch {
        if (signupState !is LoadingOperation) {
            try {
                signupViewModel.signup(name, surname, email, password, confirmPassword, birthDate)
            } catch (e: Exception) {
                // Nothing to do
            }
        }
    }

    EventricTheme {
        SignupContent(
            errorBannerIsVisible = errorBannerIsVisible,
            name = name,
            surname = surname,
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            confirmPassword = confirmPassword,
            confirmPasswordVisible = confirmPasswordVisible,
            birthDate = birthDate,
            onNameChange = ::onNameChange,
            onSurnameChange = ::onSurnameChange,
            onEmailChange = ::onEmailChange,
            onPasswordVisibleChange = ::onPasswordVisibleChange,
            onPasswordChange = ::onPasswordChange,
            onConfirmPasswordVisibleChange = ::onConfirmPasswordVisibleChange,
            onConfirmPasswordChange = ::onConfirmPasswordChange,
            onBirthDateSelected = ::onBirthDateSelected,
            onSubmit = ::onSubmit,
        )
    }
}

