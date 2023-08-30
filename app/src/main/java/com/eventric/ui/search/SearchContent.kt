package com.eventric.ui.search

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.BrandSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.EventCardCompactEmptyItem
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.ui.component.SelectorItemData
import com.eventric.ui.component.UserCardCompactEmptyItem
import com.eventric.ui.component.UserCardCompactItem
import com.eventric.vo.Event
import com.eventric.vo.User

@Composable
fun SearchContent(
    events: List<Pair<Triple<Pair<String, Event>, Boolean, Uri>, Boolean>>,
    users: List<Pair<Triple<Pair<String, User>, Boolean, Uri>, Boolean>>,
    pages: List<SelectorItemData>,
    selectedPage: SelectorItemData,
    onChangeSelectedPage: (SelectorItemData) -> Unit,
    searchWord: String,
    onChangeSearchWord: (String) -> Unit,
    goToEvent: (eventId: String) -> Unit,
    goToUser: (userId: String) -> Unit,
    onFollowClick: (String, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .padding(horizontal = 10.dp)
    ) {
        CustomTextInput(
            value = searchWord,
            hint = stringResource(R.string.search_label),
            isLastInput = true,
            iconId = R.drawable.ic_search,
            visualTransformationEnabled = false,
            passwordVisible = true,
            onValueChange = onChangeSearchWord,
        )
        Spacer(modifier = Modifier.height(20.dp))
        BrandSelector(
            modifier = Modifier.padding(horizontal = 100.dp),
            dataList = pages,
            selectedItem = selectedPage,
            onChangeSelectedItem = { selected -> onChangeSelectedPage(selected) }
        )
        AnimatedVisibility(
            visible = selectedPage == pages[0]
        )
        {
            if (!events.any { it.second })
                EventCardCompactEmptyItem(Modifier.padding(horizontal = 25.dp, vertical = 20.dp))
            else
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 25.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    items(events) { (eventTriple, isVisible) ->
                        val eventId = eventTriple.first.first
                        val event = eventTriple.first.second
                        val isFavourite = eventTriple.second
                        val uriImage = eventTriple.third
                        AnimatedVisibility(
                            visible = isVisible
                        ) {
                            EventCardCompactItem(
                                modifier = Modifier.padding(vertical = 10.dp),
                                event = event,
                                uriImage = uriImage,
                                isFavorite = isFavourite,
                                onClick = { goToEvent(eventId) }
                            )
                        }
                    }
                }
        }
        AnimatedVisibility(
            visible = selectedPage == pages[1]
        )
        {
            if (!users.any { it.second })
                UserCardCompactEmptyItem(Modifier.padding(horizontal = 25.dp, vertical = 20.dp))
            else
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 25.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    items(users) { (userTriple, isVisible) ->
                        val userId = userTriple.first.first
                        val user = userTriple.first.second
                        val isFollowed = userTriple.second
                        val uriImage = userTriple.third
                        AnimatedVisibility(
                            visible = isVisible
                        ) {
                            UserCardCompactItem(
                                modifier = Modifier.padding(vertical = 10.dp),
                                user = user,
                                isFollowed = isFollowed,
                                uriImage = uriImage,
                                onClick = { goToUser(userId) },
                                onFollowClick = { onFollowClick(userId, isFollowed) }
                            )
                        }
                    }
                }
        }
    }
}