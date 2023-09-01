package com.eventric.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eventric.R

@Composable
fun BrandAlertDialog(
    openDialog: Boolean,
    title: String,
    text: String = "",
    closeDialog: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    if (openDialog)
        AlertDialog(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colors.background,
            onDismissRequest = {
                closeDialog()
            },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSecondary
                )
            },
            text = {
                if (text != "")
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
            },
            confirmButton = {
                CustomButtonPrimary(
                    text = stringResource(R.string.confirm_dialog_label),
                    showArrowIcon = false,
                    onClick = {
                        onConfirm()
                        closeDialog()
                    }
                )
            },
            dismissButton = {
                CustomButtonSecondary(
                    text = stringResource(R.string.dismiss_dialog_label),
                    onClick = {
                        onDismiss()
                        closeDialog()
                    }
                )
            }
        )
}