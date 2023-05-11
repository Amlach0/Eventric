package com.eventric.ui.newEvent

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.eventric.BuildConfig
import com.eventric.repo.ErrorOperation
import com.eventric.repo.LoadingOperation
import com.eventric.repo.SuccessOperation
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import kotlinx.coroutines.launch

@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel = hiltViewModel()
) {

    val createEventState by createEventViewModel.createEventCodeResult.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    var name by remember { mutableStateOf(BuildConfig.USERNAME) }
    var location by remember { mutableStateOf(BuildConfig.PASSWORD) }


    fun onSubmit() = coroutineScope.launch {
        if (createEventState !is LoadingOperation) {
            try {
                createEventViewModel.createEvent(name, location)
            } catch (e: Exception) {
                // Nothing to do
            }
        }
    }
    EventricTheme {
        CreateEventContent(
            name = name,
            location = location,
            category = EventCategory.NoCategory,
            type = EventType.InviteOnly,
            onSubmit = {}
        )
    }
}


