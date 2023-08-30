package com.eventric.ui.events

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eventric.ui.component.BrandSelector
import com.eventric.ui.component.EventCardCompactEmptyItem
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.ui.component.SelectorItemData
import com.eventric.vo.Event

@Composable
fun EventsContent(
    events: List<Pair<Triple<Pair<String, Event>, Boolean, Uri>, Boolean>>,
    pages: List<SelectorItemData>,
    selectedPage: SelectorItemData,
    onChangeSelectedPage: (SelectorItemData) -> Unit,
    goToEvent: (eventId: String) -> Unit,
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
                .padding(top = 45.dp)
                .padding(horizontal = 25.dp)
        ) {
            if (!events.any { it.second })
                EventCardCompactEmptyItem(Modifier.padding(vertical = 20.dp))
            else
                LazyColumn(
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

        val colorStops = arrayOf(
            0f to MaterialTheme.colors.background,
            0.06f to MaterialTheme.colors.background,
            0.12f to Color.Transparent,
            1f to Color.Transparent
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        ) {
            BrandSelector(
                dataList = pages,
                selectedItem = selectedPage,
                onChangeSelectedItem = { selected -> onChangeSelectedPage(selected) }
            )
        }
    }
}