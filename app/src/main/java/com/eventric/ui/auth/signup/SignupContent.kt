package com.eventric.ui.auth.signup

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.ui.component.CustomButtonSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.DatePickerDialog
import com.eventric.ui.component.ImageProfilePicker
import com.eventric.ui.theme.EventricTheme


@Composable
fun SignupContent(
    navControllerForBack: NavController,
    errorBannerIsVisible: Boolean,
    errorBannerConfirmationIsVisible: Boolean,
    isEdit: Boolean,
    name: String,
    uriImage: Uri,
    surname: String,
    email: String,
    password: String,
    passwordVisible: Boolean,
    confirmPassword: String,
    confirmPasswordVisible: Boolean,
    errorBannerDeleteIsVisible: Boolean,
    birthDate: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onUriImageChange: (Uri) -> Unit,
    onSurnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordVisibleChange: (Boolean) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onBirthDateSelected: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginPressed: () -> Unit,
    onDeletePressed: () -> Unit,
) {
    var openDateDialog by remember { mutableStateOf(false) }

    if (openDateDialog) {
        DatePickerDialog(
            onDismiss = {
                openDateDialog = false
            },
            onDateSelected = {
                onBirthDateSelected(it)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_log_in_backgound),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = Color.Transparent,
            topBar = {
                if (isEdit)
                    BrandTopBar(
                        left = {
                            Back(
                                navController = navControllerForBack,
                                tint = MaterialTheme.colors.onBackground
                            )
                            Title(
                                modifier = Modifier.padding(horizontal = 13.dp),
                                title = stringResource(R.string.edit_profile),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Left
                            )
                        },
                    )
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = errorBannerIsVisible || errorBannerConfirmationIsVisible || errorBannerDeleteIsVisible,
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(MaterialTheme.colors.error)
                    ) {
                        Text(
                            text = stringResource(if (errorBannerDeleteIsVisible) R.string.error_delete_user else if (errorBannerConfirmationIsVisible) R.string.error_confirmation_psw else R.string.error_signup),
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }


                if (!isEdit)
                    Text(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 34.dp)
                            .padding(top = 10.dp),
                        text = stringResource(R.string.title_login),
                        style = MaterialTheme.typography.h4,
                        fontSize = 27.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                Spacer(modifier = Modifier.height(20.dp))
                ImageProfilePicker(
                    uri = uriImage,
                    onUriChange = onUriImageChange
                )
                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(id = R.string.name_label),
                    value = name,
                    iconId = R.drawable.ic_type_private,
                    isLastInput = false,
                    onValueChange = onNameChange
                )
                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(id = R.string.surname_label),
                    value = surname,
                    iconId = R.drawable.ic_type_private,
                    isLastInput = false,
                    onValueChange = onSurnameChange
                )
                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(id = R.string.username_label),
                    value = email,
                    iconId = R.drawable.ic_mail,
                    passwordVisible = true,
                    isLastInput = false,
                    onValueChange = onEmailChange
                )
                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(if (isEdit) R.string.new_pwd_label else R.string.pwd_label),
                    value = password,
                    iconId = R.drawable.ic_pwd,
                    onValueChange = onPasswordChange,
                    visualTransformationEnabled = true,
                    passwordVisible = passwordVisible,
                    onPasswordVisibleChange = onPasswordVisibleChange,
                    isLastInput = false,
                )
                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(if (isEdit) R.string.new_confirm_pwd_label else R.string.confirm_pwd_label),
                    value = confirmPassword,
                    iconId = R.drawable.ic_pwd,
                    onValueChange = onConfirmPasswordChange,
                    visualTransformationEnabled = true,
                    passwordVisible = confirmPasswordVisible,
                    onPasswordVisibleChange = onConfirmPasswordVisibleChange,
                    isLastInput = false,
                )

                CustomButtonSelector(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    text = if (birthDate == "") stringResource(R.string.select_date) else birthDate,
                    iconId = R.drawable.ic_calendar,
                    onClick = { openDateDialog = true }
                )

                CustomTextInput(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .padding(top = 18.dp),
                    hint = stringResource(R.string.bio_label),
                    value = bio,
                    iconId = R.drawable.ic_info,
                    onValueChange = onBioChange,
                    passwordVisible = true,
                    isLastInput = true,
                )
                Spacer(Modifier.height(200.dp))
            }
        }

        val colorStops = arrayOf(
            0f to Color.Transparent,
            0.7f to Color.Transparent,
            0.85f to MaterialTheme.colors.background,
            1f to MaterialTheme.colors.background
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
                .padding(57.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CustomButtonPrimary(
                text = stringResource(id = if (isEdit) R.string.common_edit else R.string.common_signup),
                onClick = { onSubmit() }
            )
            Spacer(Modifier.height(17.dp))
            CustomButtonSecondary(
                text = stringResource(id = if (isEdit) R.string.delete_account else R.string.common_login),
                color = if (isEdit) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                iconId = if (isEdit) R.drawable.ic_delete else null,
                onClick = { if (isEdit) onDeletePressed() else onLoginPressed() }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignupContentPreview() {
    EventricTheme {
        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPassword by remember { mutableStateOf("") }
        var confirmPasswordVisible by remember { mutableStateOf(false) }
        var birthDate by remember { mutableStateOf("") }

        SignupContent(
            navControllerForBack = rememberNavController(),
            isEdit = false,
            name = name,
            surname = surname,
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            confirmPassword = confirmPassword,
            confirmPasswordVisible = confirmPasswordVisible,
            errorBannerIsVisible = false,
            errorBannerConfirmationIsVisible = false,
            birthDate = birthDate,
            onNameChange = {
                name = it
            },
            onSurnameChange = {
                surname = it
            },
            onEmailChange = {
                email = it
            },
            onPasswordChange = {
                password = it
            },
            onPasswordVisibleChange = {
                passwordVisible = it
            },
            onConfirmPasswordChange = {
                confirmPassword = it
            },
            onConfirmPasswordVisibleChange = {
                confirmPasswordVisible = it
            },
            onBirthDateSelected = {
                birthDate = it
            },
            onSubmit = {},
            onLoginPressed = {},
            onDeletePressed = {},
            bio = "",
            onBioChange = {},
            uriImage = Uri.EMPTY,
            onUriImageChange = {},
            errorBannerDeleteIsVisible = false
        )
    }
}

