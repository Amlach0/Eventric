package com.eventric.ui.events

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eventric.ui.component.BrandSelector
import com.eventric.ui.component.EventCardCompactItem
import com.eventric.ui.component.SelectorItemData
import com.eventric.vo.Event

@Composable
fun EventsContent(
    events: List<Pair<Triple<String, Boolean, Event>, Boolean>>,
    pages: List<SelectorItemData>,
    selectedPage: SelectorItemData,
    onChangeSelectedPage: (SelectorItemData) -> Unit,
    goToEvent: (eventId: String) -> Unit
) {
    Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 25.dp)
        .padding(horizontal = 10.dp)
)   {
        BrandSelector(
            dataList = pages,
            selectedItem = selectedPage,
            onChangeSelectedItem = {selected -> onChangeSelectedPage(selected) }
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 25.dp),
            contentPadding = PaddingValues(vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(events) { (event, isVisible) ->
                AnimatedVisibility(
                    visible = isVisible
                ){
                    EventCardCompactItem(
                        event = event.third,
                        isFavorite = event.second,
                        onClick = { goToEvent(event.first) }
                    )
                }
            }
        }
    }
}