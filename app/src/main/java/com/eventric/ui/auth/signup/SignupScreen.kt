package com.eventric.ui.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.SuccessOperation
import com.eventric.vo.User
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    id: String = "",
    signupViewModel: SignupViewModel = hiltViewModel(),
    goToDispatcher: () -> Unit,
) {
    var isEdit by remember { mutableStateOf(false) }
    if (id != "") {
        signupViewModel.setUserId(id)
        isEdit = true
    }

    val signupState by signupViewModel.signupCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var errorBannerIsVisible by remember { mutableStateOf(false) }

    var errorBannerConfermationIsVisible by remember { mutableStateOf(false) }

    val user by signupViewModel.userFlow.collectAsStateWithLifecycle(User())
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var birthDate by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(signupState) {
        if (signupState is ErrorOperation) errorBannerIsVisible = true
        if (signupState is SuccessOperation) goToDispatcher()
    }

    LaunchedEffect(user)
    {
        name = user.name.toString()
        surname = user.surname.toString()
        email = user.email
        birthDate = user.birthDate.toString()
        bio = user.bio.toString()
    }

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
        errorBannerConfermationIsVisible = (password != confirmPassword)
    }

    fun onConfirmPasswordVisibleChange(value: Boolean) {
        confirmPasswordVisible = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
        errorBannerConfermationIsVisible = (password != confirmPassword)
    }

    fun onBirthDateSelected(value: String) {
        birthDate = value
    }

    fun onBioChange(value: String) {
        bio = value
    }

    fun onSubmit() = coroutineScope.launch {
        if (password == confirmPassword) {
            if (signupState !is LoadingOperation) {
                try {
                    signupViewModel.signupOrEdit(
                        name,
                        surname,
                        email,
                        password,
                        confirmPassword,
                        birthDate,
                        bio,
                        isEdit
                    )
                } catch (e: Exception) {
                    // Nothing to do
                }
            }
        }
    }

    fun onLoginPressed() {
        goToDispatcher()
    }

    fun onDeletePressed() = coroutineScope.launch {
        signupViewModel.deleteUser()
        goToDispatcher()
    }

    EventricTheme {
        SignupContent(
            errorBannerIsVisible = errorBannerIsVisible,
            errorBannerConfirmationIsVisible = errorBannerConfermationIsVisible,
            isEdit = isEdit,
            name = name,
            surname = surname,
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            confirmPassword = confirmPassword,
            confirmPasswordVisible = confirmPasswordVisible,
            birthDate = birthDate,
            bio = bio,
            onNameChange = ::onNameChange,
            onSurnameChange = ::onSurnameChange,
            onEmailChange = ::onEmailChange,
            onPasswordVisibleChange = ::onPasswordVisibleChange,
            onPasswordChange = ::onPasswordChange,
            onConfirmPasswordVisibleChange = ::onConfirmPasswordVisibleChange,
            onConfirmPasswordChange = ::onConfirmPasswordChange,
            onBirthDateSelected = ::onBirthDateSelected,
            onBioChange = ::onBioChange,
            onSubmit = ::onSubmit,
            onLoginPressed = ::onLoginPressed,
            onDeletePressed = ::onDeletePressed,
        )
    }
}


