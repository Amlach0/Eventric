package com.eventric.ui.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.eventric.R
import com.eventric.ui.component.BrandSelector
import com.eventric.ui.component.BrandTopBar
import com.eventric.ui.component.CustomButtonSecondary
import com.eventric.ui.component.EventCardCompactEmptyItem
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.ui.component.FollowedAndFollowersCounters
import com.eventric.ui.component.ProfileEmptyItem
import com.eventric.ui.component.ProfileItem
import com.eventric.ui.component.SelectorItemData
import com.eventric.vo.Event
import com.eventric.vo.User

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileContent(
    navController: NavController,
    user: User,
    uriImage: Uri,
    isInHome: Boolean,
    isLoggedUser: Boolean,
    isUserFollowed: Boolean,
    followers: List<Triple<String, User, Uri>>,
    followed: List<Triple<String, User, Uri>>,
    sheetState: ModalBottomSheetState,
    isFollowersSheet: Boolean,
    pages: List<SelectorItemData>,
    selectedPage: SelectorItemData,
    organizedEvents: List<Triple<Pair<String, Event>, Boolean, Uri>>,
    onFollowChange: () -> Unit,
    onEdit: () -> Unit,
    onUser: (String) -> Unit,
    onLogout: () -> Unit,
    onShowFollowed: () -> Unit,
    onShowFollowers: () -> Unit,
    onChangeSelectedPage: (new: SelectorItemData) -> Unit,
    onEvent: (String) -> Unit,
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
                    text = stringResource(if (isFollowersSheet){ if(!isLoggedUser) R.string.followers_label else R.string.other_user_followers_label } else R.string.followed_label),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSecondary
                )
                if ((isFollowersSheet && followers.isEmpty()) || (!isFollowersSheet && followed.isEmpty()))
                    ProfileEmptyItem(Modifier.padding(vertical = 25.dp))
                else
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 25.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = if (isFollowersSheet) followers else followed) { (userId, user, uriImage) ->
                            ProfileItem(
                                name = "${user.name} ${user.surname}",
                                uriImage = uriImage,
                                isInvited = false,
                                showInviteButton = false,
                                onInviteClick = { },
                                onClick = { onUser(userId) },
                            )
                        }
                    }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                BrandTopBar(
                    left = {
                        if (!isInHome)
                            Back(
                                navController = navController,
                                tint = MaterialTheme.colors.onBackground
                            )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 30.dp)
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    model = if (uriImage == Uri.EMPTY) R.drawable.img_profile_placeholder else uriImage,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.img_profile_placeholder),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = user.name + " " + user.surname,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSecondary
                )

                FollowedAndFollowersCounters(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    isLoggedUser = isLoggedUser || isInHome,
                    numberOfFollowed = followed.size,
                    numberOfFollowers = followers.size,
                    onFollowedClick = { onShowFollowed() },
                    onFollowersClick = { onShowFollowers() }
                )

                Column(
                    Modifier
                        .width(160.dp)
                        .padding(top = 21.dp)
                ) {
                    if (isInHome) {
                        CustomButtonSecondary(
                            modifier = Modifier
                                .width(180.dp),
                            text = stringResource(R.string.edit_profile),
                            iconId = R.drawable.ic_edit,
                            onClick = onEdit,
                        )
                        CustomButtonSecondary(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(top = 10.dp),
                            text = stringResource(R.string.logout),
                            iconId = R.drawable.ic_logout,
                            onClick = onLogout,
                            color = MaterialTheme.colors.error
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    } else if (!isLoggedUser)
                        CustomButtonSecondary(
                            text = stringResource(if (isUserFollowed) R.string.unfollow_label else R.string.follow_label),
                            modifier = Modifier
                                .width(200.dp),
                            iconId = if (isUserFollowed) R.drawable.ic_unfollow else R.drawable.ic_follow,
                            onClick = onFollowChange
                        )
                }

                Column(
                    Modifier
                        .padding(top = 30.dp)
                ) {
                    if (isInHome) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(R.string.bio_label),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.onSecondary
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = user.bio ?: "nessuna bio",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 25.dp)
                            ) {
                                when (selectedPage.value) {
                                    stringResource(R.string.bio_label) -> {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 15.dp, vertical = 40.dp),
                                            text = user.bio ?: "nessuna bio",
                                            style = MaterialTheme.typography.body2,
                                            color = MaterialTheme.colors.onBackground
                                        )
                                    }

                                    stringResource(R.string.event) -> {
                                        if (organizedEvents.isEmpty())
                                            EventCardCompactEmptyItem(Modifier.padding(horizontal = 15.dp, vertical = 40.dp))
                                        else
                                            LazyColumn(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 15.dp),
                                                contentPadding = PaddingValues(vertical = 40.dp),
                                                verticalArrangement = Arrangement.spacedBy(15.dp)
                                            ) {
                                                items(organizedEvents) { (eventPair, isFavorite, uriImage) ->
                                                    val eventId = eventPair.first
                                                    val event = eventPair.second
                                                    EventCardCompactItem(
                                                        event = event,
                                                        uriImage = uriImage,
                                                        isFavorite = isFavorite,
                                                        onClick = { onEvent(eventId) }
                                                    )
                                                }
                                            }
                                    }
                                }
                            }

                            val colorStops = arrayOf(
                                0f to MaterialTheme.colors.background,
                                0.07f to MaterialTheme.colors.background,
                                0.15f to Color.Transparent,
                                0.9f to Color.Transparent,
                                1f to MaterialTheme.colors.background
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(colorStops = colorStops))
                            ) {
                                BrandSelector(
                                    dataList = pages,
                                    selectedItem = selectedPage,
                                    onChangeSelectedItem = { onChangeSelectedPage(it) }
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
