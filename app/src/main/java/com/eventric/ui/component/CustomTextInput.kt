package com.eventric.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    isLastInput: Boolean,
    @DrawableRes icon: Int? = null,
    visualTransformationEnabled: Boolean = false,
    onValueChange: (String) -> Unit = {},
    passwordVisible: Boolean = false,
    onPasswordVisibleChange: (Boolean) -> Unit = {},
    onDone: () -> Unit = {},
) {

    val focusManager = LocalFocusManager.current

    Column(modifier) {
        if (visualTransformationEnabled) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.small
                    ),
                shape = MaterialTheme.shapes.small,
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.body2,
                singleLine = true,
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = if (icon != null) {
                    {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }

                } else null,
                trailingIcon = {
                    IconButton(
                        onClick = { onPasswordVisibleChange(!passwordVisible) }
                    ) {
                        Icon(
                            painter = if (passwordVisible) painterResource(R.drawable.ic_hide_pwd) else painterResource(
                                R.drawable.ic_show_pwd
                            ),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = if (isLastInput) ImeAction.Done else ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onDone = { onDone() }
                ),
            )
        } else {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.small
                    ),
                shape = MaterialTheme.shapes.small,
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.body2,
                singleLine = true,
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                leadingIcon = if (icon != null) {
                    {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }

                } else null,
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = if (isLastInput) ImeAction.Done else ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onDone = { onDone() }
                ),
            )
        }
    }
}


@Preview
@Composable
fun CustomTextInputPreview() {

    var value by remember { mutableStateOf("") }

    EventricTheme {
        CustomTextInput(
            value = value,
            hint = "test",
            isLastInput = false,
            onValueChange = {
                value = it
            },
            passwordVisible = false,
            onPasswordVisibleChange = {}
        ) {}
    }
}

