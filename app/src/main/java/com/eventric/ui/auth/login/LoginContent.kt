package com.eventric.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import com.eventric.ui.component.CustomButtonSubmit
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.theme.EventricTheme

@Composable
fun LoginContent(
    email: String,
    password: String,
    passwordVisible: Boolean,
    errorBannerIsVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        /*
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
         */
        //TODO backgound
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AnimatedVisibility(
                visible = errorBannerIsVisible,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = stringResource(R.string.error_login),
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
                style = MaterialTheme.typography.titleLarge,
                fontSize = 27.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextInput(
                modifier = Modifier.padding(horizontal = 34.dp),
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
                isLastInput = true,
                onDone = onSubmit,
            )
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
            //TODO registrarsi
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    EventricTheme {
        LoginContent(
            email = email,
            password = password,
            passwordVisible = passwordVisible,
            errorBannerIsVisible = false,
            onPasswordVisibleChange = {
                passwordVisible = it
            },
            onEmailChange = {
                email = it
            },
            onPasswordChange = {
                password = it
            }
        ) {

        }
    }
}
