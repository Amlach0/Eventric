package com.eventric.ui.search

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eventric.R
import com.eventric.ui.component.CustomTextInput
import com.eventric.ui.component.EventCardCompactEmptyItem
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.vo.Event

@Composable
fun SearchContent(
    events: List<Pair<Triple<Pair<String, Event>, Boolean, Uri>, Boolean>>,
    searchWord: String,
    onChangeSearchWord: (String) -> Unit,
    goToEvent: (eventId: String) -> Unit,
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
}