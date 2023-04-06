package com.eventric.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eventric.R

@Composable
fun CustomButtonSubmit(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
){
    Column(modifier) {
        Button(
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(12.dp),
            onClick = { onClick() },
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Icon(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .padding(8.dp),
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Preview
@Composable
fun CustomButtonSubmitPreview(){
    CustomButtonSubmit(
        modifier = Modifier,
        text = "Test"
    ) {

    }
}