package com.eventric.ui.dispatcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.eventric.R
import com.eventric.ui.component.FullScreenLoader

@Composable
fun DispatcherContent(
    dispatcherState: DispatcherState
) {

        when (dispatcherState) {
            DispatcherState.SPLASH ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "logo",
                    contentScale = ContentScale.None,
                    alignment = Alignment.Center
                )
            else -> FullScreenLoader(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
            )
    }
}