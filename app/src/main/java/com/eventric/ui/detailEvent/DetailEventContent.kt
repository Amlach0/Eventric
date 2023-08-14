package com.eventric.ui.detailEvent

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.ui.component.EventCategoryItem
import com.eventric.ui.component.EventInfoItem
import com.eventric.ui.component.ProfileOrganizerItem
import com.eventric.ui.component.ProfileGoingExpandedItem
import com.eventric.vo.Event
import com.eventric.vo.EventCategory

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailEventContent(
    navController: NavController,
    id: String,
    event: Event,
    organizerName: String,
    isFavorite: Boolean,
    isRegistrationOpen: Boolean,
    isUserOrganizer: Boolean,
    isUserSubscribed: Boolean,
    isOrganizerFollowed: Boolean,
    onEditClick: () -> Unit,
    onInviteClick: () -> Unit,
    onGoingClick: () -> Unit,
    onFavoriteChange: () -> Unit,
    onFollowChange: () -> Unit,
    onSubscribeChange: (Boolean) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            painter = painterResource(R.drawable._794d36030d9c5d830e431721e08ddd7),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = Color.Transparent,
            topBar = {
                BrandTopBar(
                    left = {
                        Back(
                            navController = navController,
                        )
                        Title(
                            modifier = Modifier.padding(horizontal = 13.dp),
                            title = stringResource(R.string.info_event),
                            textAlign = TextAlign.Left
                        )
                    },
                    right = {
                        if (isUserOrganizer)
                        //TODO check proprietario o organizzatore
                        {
                            ActionButton(
                                onClick = { onEditClick() },
                                iconId = R.drawable.ic_edit,
                            )
                            Spacer(Modifier.width(15.dp))
                        }
                        ActionButton(
                            onClick = { onFavoriteChange() },
                            iconId = if (isFavorite) {
                                R.drawable.ic_bookmark_full
                            } else {
                                R.drawable.ic_bookmark_empty
                            }
                        )
                    }
                )
            }
        ) {

            Column {

                Spacer(modifier = Modifier.height(140.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    EventCategoryItem(
                        category = EventCategory.fromDbString(event.category.toString()),
                        selected = true,
                        showLabel = false,
                    )
                    ProfileGoingExpandedItem(
                        modifier = Modifier.padding(start = 20.dp),
                        numberOfGoing = event.subscribed.size,
                        onClick = { onGoingClick() },
                        onInviteClick = { onInviteClick() },
                    )
                }

                Column(
                    Modifier
                        .weight(1f)
                        .padding(24.dp)
                ) {
                    Text(
                        text = event.name ?: "loading",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSecondary
                    )

                    EventInfoItem(
                        modifier = Modifier.padding(top = 18.dp),
                        iconId = R.drawable.ic_calendar,
                        primaryText = event.date?.start.toString(),
                        secondaryText = event.date?.end.toString()
                    )
                    EventInfoItem(
                        modifier = Modifier.padding(top = 16.dp),
                        iconId = R.drawable.ic_location,
                        primaryText = event.location.toString(),
                    )
                    ProfileOrganizerItem(
                        modifier = Modifier.padding(top = 24.dp),
                        name = organizerName,
                        imageId = R.drawable._27a1f62a2d277788a853d203733f8d5,
                        isFollowed = isOrganizerFollowed,
                        showFollowButton = !isUserOrganizer,
                        onFollowClick = { onFollowChange() }
                    )

                    Text(
                        modifier = Modifier.padding(top = 21.dp),
                        text = stringResource(R.string.info_label),
                        style = MaterialTheme.typography.h1,
                        fontSize = 27.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = event.info ?: "",
                        color = MaterialTheme.colors.onBackground
                    )
                }

                if (!isUserOrganizer)
                    Column(
                        modifier = Modifier.padding(horizontal = 52.dp, vertical = 23.dp)
                    ) {
                        if (isRegistrationOpen && !isUserSubscribed)
                            CustomButtonPrimary(
                                text = stringResource(id = R.string.common_subscribe),
                                contentPadding = PaddingValues(
                                    vertical = 18.dp,
                                    horizontal = 12.dp
                                ),
                                onClick = { onSubscribeChange(true) }
                            )
                        else {
                            CustomButtonSecondary(
                                text = stringResource(if (isUserSubscribed) R.string.common_unsubscribe else R.string.common_close_subscribe),
                                enabled = isUserSubscribed,
                                color = if (!isUserSubscribed) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                                onClick = { onSubscribeChange(false) }
                            )
                        }
                        Text(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(8.dp),
                            text = "Dal ${event.dateRegistration?.start} al ${event.dateRegistration?.end}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground
                        )
                    }

            }
        }
    }
}