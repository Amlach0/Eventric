package com.eventric.ui.detailEvent

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.eventric.R
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonPrimary
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.ui.component.EventCategoryItem
import com.eventric.ui.component.EventInfoItem
import com.eventric.ui.component.ProfileEmptyItem
import com.eventric.ui.component.ProfileGoingExpandedItem
import com.eventric.ui.component.ProfileItem
import com.eventric.ui.component.ProfileOrganizerItem
import com.eventric.vo.Event
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import com.eventric.vo.User

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailEventContent(
    navController: NavController,
    event: Event,
    uriImage: Uri,
    organizerName: String,
    uriOrganizerImage: Uri,
    isFavorite: Boolean,
    isRegistrationOpen: Boolean,
    isUserOrganizer: Boolean,
    isUserSubscribed: Boolean,
    isOrganizerFollowed: Boolean,
    sheetState: ModalBottomSheetState,
    isInviteSheet: Boolean,
    subscribedUsers: List<Triple<Pair<String, User>, Boolean, Uri>>,
    invitableUsers: List<Triple<Pair<String, User>, Boolean, Uri>>,
    onEdit: () -> Unit,
    onUser: (String) -> Unit,
    onOrganizer: () -> Unit,
    onInvite: () -> Unit,
    onGoing: () -> Unit,
    onFavoriteChange: () -> Unit,
    onFollowChange: () -> Unit,
    onUserInviteChange: (userId: String) -> Unit,
    onSubscribeChange: (Boolean) -> Unit,
) {


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 34.dp)
            ) {
                Text(
                    text = stringResource(if (isInviteSheet) R.string.invite_list_label else R.string.subscribed_list_label),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSecondary
                )
                if ((isInviteSheet && invitableUsers.isEmpty()) || (!isInviteSheet && subscribedUsers.isEmpty()))
                    ProfileEmptyItem(Modifier.padding(vertical = 25.dp))
                else
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 25.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = if (isInviteSheet) invitableUsers else subscribedUsers) { (userPair, isInvited, uriImage) ->
                            val userId = userPair.first
                            val user = userPair.second
                            ProfileItem(
                                name = "${user.name} ${user.surname}",
                                uriImage = uriImage,
                                isInvited = isInvited,
                                showInviteButton = isInviteSheet,
                                onInviteClick = { onUserInviteChange(userId) },
                                onClick = { onUser(userId) },
                            )
                        }
                    }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                model = if (uriImage == Uri.EMPTY) R.drawable.img_event_placeholder else uriImage,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_event_placeholder),
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
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Left
                            )
                        },
                        right = {
                            if (isUserOrganizer) {
                                ActionButton(
                                    onClick = { onEdit() },
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
                            onClick = { onGoing() },
                            onInviteClick = { onInvite() },
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
                            primaryText = if(event.location!="") event.location.toString() else stringResource(R.string.empty_location_label),
                        )
                        EventInfoItem(
                            modifier = Modifier.padding(top = 16.dp),
                            iconId = EventType.fromDbString(event.type.toString()).icon,
                            primaryText = "Evento "+EventType.fromDbString(event.type.toString()).title
                        )
                        ProfileOrganizerItem(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        color = MaterialTheme.colors.onPrimary,
                                        bounded = false
                                    )
                                ) { onOrganizer() },
                            name = organizerName,
                            uriImage = uriOrganizerImage,
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
}