package com.eventric.ui.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eventric.R
import com.eventric.ui.theme.EventricTheme

@Composable
fun ImageEventPicker(
    uri: Uri,
    @DrawableRes placeholderId: Int = R.drawable.img_event_placeholder,
    onUriChange: (Uri) -> Unit,
) {
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            onUriChange(it ?: Uri.EMPTY)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = MaterialTheme.shapes.medium
                ),
            model = if (uri== Uri.EMPTY) placeholderId else uri,
            contentDescription = null,
            placeholder = painterResource(placeholderId),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            CustomButtonSecondary(
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 5.dp),
                text = stringResource(R.string.choose_event_image_label),
                contentPadding = PaddingValues(0.dp),
                iconId = null,
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )

        }
    }
}

@Composable
fun ImageProfilePicker(
    uri: Uri,
    @DrawableRes placeholderId: Int = R.drawable.img_profile_placeholder,
    onUriChange: (Uri) -> Unit,
) {
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            onUriChange(it ?: Uri.EMPTY)
        }
    )

    Box(
        modifier = Modifier
            .height(120.dp)
            .aspectRatio(1f)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape
                ),
            model = if (uri== Uri.EMPTY) placeholderId else uri,
            contentDescription = null,
            placeholder = painterResource(placeholderId),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CustomButtonSecondary(
                modifier = Modifier
                    .width(100.dp)
                    .padding(5.dp),
                text = stringResource(R.string.choose_event_image_label),
                contentPadding = PaddingValues(0.dp),
                iconId = null,
                onClick = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )

        }
    }
}

@Preview
@Composable
fun ImageEventPickerPreview() {
    EventricTheme() {
        ImageEventPicker(
            uri = Uri.EMPTY,
            onUriChange = {}
        )
    }
}

@Preview
@Composable
fun ImageProfilePickerPreview() {
    EventricTheme() {
        ImageProfilePicker(
            uri = Uri.EMPTY,
            onUriChange = {}
        )
    }
}