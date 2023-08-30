package com.eventric.ui.auth.signup

import android.net.Uri
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eventric.ui.component.FullScreenLoader
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.SuccessOperation
import com.eventric.vo.User
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navControllerForBack: NavController = rememberNavController(),
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
    val deleteUserState by signupViewModel.deleteUserCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    val user by signupViewModel.userFlow.collectAsStateWithLifecycle(User())
    val dbUriImage by signupViewModel.uriImageFlow.collectAsStateWithLifecycle(Uri.EMPTY)
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var uriImage by remember { mutableStateOf(Uri.EMPTY) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var birthDate by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    var errorBannerIsVisible by remember { mutableStateOf(false) }
    var errorBannerDeleteIsVisible by remember { mutableStateOf(false) }
    var errorBannerConfermationIsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(signupState, deleteUserState) {
        if (signupState is ErrorOperation && !isEdit) errorBannerIsVisible = true
        if (deleteUserState is ErrorOperation) errorBannerDeleteIsVisible = true
        if (signupState is SuccessOperation) goToDispatcher()
        if (deleteUserState is SuccessOperation) goToDispatcher()
    }

    LaunchedEffect(user, dbUriImage)
    {
        name = user.name.toString()
        surname = user.surname.toString()
        uriImage = dbUriImage
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

    fun onUriImageChange(value: Uri) {
        uriImage = value
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
                        name = name,
                        surname = surname,
                        uriImage = uriImage,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        birthDate = birthDate,
                        bio = bio,
                        isEdit = isEdit,
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
        if (signupState is LoadingOperation || signupState is SuccessOperation || deleteUserState is LoadingOperation || deleteUserState is SuccessOperation)
            FullScreenLoader()
        else
            SignupContent(
                navControllerForBack = navControllerForBack,
                isEdit = isEdit,
                name = name,
                uriImage = uriImage,
                surname = surname,
                email = email,
                password = password,
                passwordVisible = passwordVisible,
                confirmPassword = confirmPassword,
                confirmPasswordVisible = confirmPasswordVisible,
                birthDate = birthDate,
                bio = bio,
                errorBannerIsVisible = errorBannerIsVisible,
                errorBannerConfirmationIsVisible = errorBannerConfermationIsVisible,
                errorBannerDeleteIsVisible = errorBannerDeleteIsVisible,
                onNameChange = ::onNameChange,
                onUriImageChange = ::onUriImageChange,
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


