package com.eventric.ui.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eventric.R
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.ui.component.CustomButtonSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.DatePickerDialog
import com.eventric.ui.theme.EventricTheme


@Composable
fun SignupContent(
    errorBannerIsVisible: Boolean,
    errorBannerConfirmationIsVisible: Boolean,
    name: String,
    surname: String,
    email: String,
    password: String,
    passwordVisible: Boolean,
    confirmPassword: String,
    confirmPasswordVisible: Boolean,
    birthDate: String,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordVisibleChange: (Boolean) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onBirthDateSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginPressed: () -> Unit
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

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            AnimatedVisibility(
                visible = errorBannerIsVisible,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colors.error)
                ) {
                    Text(
                        text = stringResource(R.string.error_signup),
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            AnimatedVisibility(
                visible = errorBannerConfirmationIsVisible,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colors.error)
                ) {
                    Text(
                        text = stringResource(R.string.error_confirmation_psw),
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo",
                contentScale = ContentScale.None,
                alignment = Alignment.Center
            )
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = stringResource(id = R.string.title_login),
                style = MaterialTheme.typography.h4,
                fontSize = 27.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextInput(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp),
                hint = stringResource(id = R.string.name_label),
                value = name,
                icon = R.drawable.ic_type_private,
                isLastInput = false,
                onValueChange = onNameChange
            )
            CustomTextInput(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp),
                hint = stringResource(id = R.string.surname_label),
                value = surname,
                icon = R.drawable.ic_type_private,
                isLastInput = false,
                onValueChange = onSurnameChange
            )
            CustomTextInput(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp),
                hint = stringResource(id = R.string.username_label),
                value = email,
                icon = R.drawable.ic_mail,
                passwordVisible = true,
                isLastInput = false,
                onValueChange = onEmailChange
            )
            CustomTextInput(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp),
                hint = stringResource(R.string.pwd_label),
                value = password,
                icon = R.drawable.ic_pwd,
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
                hint = stringResource(R.string.confirm_pwd_label),
                value = confirmPassword,
                icon = R.drawable.ic_pwd,
                onValueChange = onConfirmPasswordChange,
                visualTransformationEnabled = true,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibleChange = onConfirmPasswordVisibleChange,
                isLastInput = true,
            )

            CustomButtonSelector(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp),
                text = if (birthDate == "") stringResource(R.string.select_date) else birthDate,
                iconId = R.drawable.ic_calendar,
                onClick = { openDateDialog = true }
            )

            Spacer(Modifier.weight(1F))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(57.dp)
            ) {
                CustomButtonPrimary(
                    text = stringResource(id = R.string.common_signup),
                    onClick = { onSubmit() }
                )
                Spacer(Modifier.height(17.dp))
                CustomButtonSecondary(
                    text = stringResource(id = R.string.common_login),
                    onClick = { onLoginPressed() }
                )
            }
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
            onLoginPressed = {}
        )
    }
}

