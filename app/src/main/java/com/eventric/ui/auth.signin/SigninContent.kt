package com.eventric.ui.auth.signin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eventric.R
import com.eventric.ui.component.CustomButtonSubmit
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.theme.EventricTheme
import java.util.Calendar


@Composable
fun SigninContent(
    errorBannerIsVisible: Boolean,
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
) {
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
                        text = stringResource(R.string.error_signin),
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

            // DATA

            val context = LocalContext.current
            val calendar = Calendar.getInstance()

            // data inizio

            var selectedDateText by remember { mutableStateOf("") }

            // recupero la data attuale
            val Year = calendar[Calendar.YEAR]
            val Month = calendar[Calendar.MONTH]
            val DayOfMonth = calendar[Calendar.DAY_OF_MONTH]

            //creo il dialog per la selezione della data
            val startDatePicker = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"; onBirthDateSelected(selectedDateText)
                }, Year, Month, DayOfMonth
            )
            startDatePicker.datePicker.maxDate = calendar.timeInMillis

            Button(
                modifier = Modifier
                    .padding(horizontal = 34.dp)
                    .padding(top = 18.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { startDatePicker.show() },
                colors = ButtonDefaults.buttonColors( backgroundColor =  MaterialTheme.colors.onBackground)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier
                            .padding(8.dp),
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = MaterialTheme.colors.background
                    )
                    Text(
                        text = if(selectedDateText.isEmpty()){ "Data di nascita" } else { selectedDateText },
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 15.sp,
                    )
                }
            }
            Spacer(Modifier.weight(1F))
            //TODO Remember me
            //TODO Forgot pwd
            CustomButtonSubmit(
            modifier = Modifier
                .fillMaxWidth()
                .padding(57.dp)
                ,
                text = stringResource(id = R.string.common_login),
                onClick = { onSubmit() }
            )
            //TODO login con google etc
        }
    }

}
@Preview(showBackground = true)
@Composable
fun SigninContentPreview() {
    EventricTheme {
        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPassword by remember { mutableStateOf("") }
        var confirmPasswordVisible by remember { mutableStateOf(false) }
        var birthDate by remember { mutableStateOf("") }

        SigninContent(
            name = name,
            surname = surname,
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            confirmPassword = confirmPassword,
            confirmPasswordVisible = confirmPasswordVisible,
            errorBannerIsVisible = false,
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
            onSubmit = {}
        )
    }
}
