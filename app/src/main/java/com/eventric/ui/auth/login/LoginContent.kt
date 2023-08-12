package com.eventric.ui.auth.login

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
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
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
    onSignupPressed: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
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
                        .background(MaterialTheme.colors.error)
                ) {
                    Text(
                        text = stringResource(R.string.error_login),
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(57.dp)
            ) {
                CustomButtonPrimary(
                    text = stringResource(id = R.string.common_login),
                    onClick = { onSubmit() }
                )

                Spacer(Modifier.height(17.dp))
                //TODO login con google etc
                CustomButtonSecondary(
                    text = stringResource(id = R.string.common_signup),
                    onClick = { onSignupPressed() }
                )
            }
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
            },
            onSubmit = {},
            onSignupPressed = {}
        )
    }
}
