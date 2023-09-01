package com.eventric.ui.search

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.BrandSelector
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.EventCardCompactEmptyItem
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.ui.component.EventCategoryCompactItem
import com.eventric.ui.component.SelectorItemData
import com.eventric.ui.component.UserCardCompactEmptyItem
import com.eventric.ui.component.UserCardCompactItem
import com.eventric.vo.Event
import com.eventric.vo.EventCategory
import com.eventric.vo.User

@Composable
fun SearchContent(
    events: List<Pair<Triple<Pair<String, Event>, Boolean, Uri>, Boolean>>,
    users: List<Pair<Triple<Pair<String, User>, Boolean, Uri>, Boolean>>,
    pages: List<SelectorItemData>,
    selectedPage: SelectorItemData,
    categoryList: List<EventCategory>,
    selectedCategory: EventCategory,
    searchWord: String,
    onSelectedCategoryChange: (EventCategory) -> Unit,
    onChangeSelectedPage: (SelectorItemData) -> Unit,
    onChangeSearchWord: (String) -> Unit,
    goToEvent: (eventId: String) -> Unit,
    goToUser: (userId: String) -> Unit,
    onFollowClick: (String, Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .padding(horizontal = 10.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .padding(horizontal = 25.dp)
        ){
            AnimatedVisibility(
                visible = selectedPage == pages[0],
                modifier = Modifier.padding(top = 90.dp)
            )
            {
                if (!events.any { it.second })
                    EventCardCompactEmptyItem(Modifier.padding(vertical = 60.dp))
                else
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 60.dp),
                    ) {
                        items(events) { (eventTriple, isVisible) ->
                            val eventId = eventTriple.first.first
                            val event = eventTriple.first.second
                            val isFavourite = eventTriple.second
                            val uriImage = eventTriple.third
                            AnimatedVisibility(
                                visible = isVisible && (selectedCategory.dbString == EventCategory.All.dbString || (event.category == selectedCategory.dbString))
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
                    UserCardCompactEmptyItem(Modifier.padding(vertical = 60.dp))
                else
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 60.dp)
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

        val colorStops = arrayOf(
            0f to MaterialTheme.colors.background,
            0.15f to MaterialTheme.colors.background,
            0.24f to Color.Transparent,
            1f to Color.Transparent
        )
        val colorStops2 = arrayOf(
            0f to MaterialTheme.colors.background,
            0.30f to MaterialTheme.colors.background,
            0.34f to Color.Transparent,
            1f to Color.Transparent
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = if(selectedPage == pages[0]) colorStops2 else colorStops))
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
            Row() {
                BrandSelector(
                    modifier = Modifier
                        .padding(horizontal = 80.dp),
                    dataList = pages,
                    selectedItem = selectedPage,
                    onChangeSelectedItem = { selected -> onChangeSelectedPage(selected) }
                )
            }
            AnimatedVisibility(visible = selectedPage == pages[0]) {
                //Row of categories
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items(items = categoryList) { category ->
                        EventCategoryCompactItem(
                            category = category,
                            selected = category == selectedCategory,
                            onClick = { onSelectedCategoryChange(category) }
                        )
                    }
                }
            }
        }
    }
}