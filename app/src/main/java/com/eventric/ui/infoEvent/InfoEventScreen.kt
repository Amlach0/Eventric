package com.eventric.ui.infoEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.utils.LoadingOperation

@Composable
fun InfoEventScreen(
    navController: NavController,
    infoEventViewModel: InfoEventViewModel = hiltViewModel()
) {
    val infoEventState by infoEventViewModel.infoEventCodeResult.collectAsState()

    var name by remember { mutableStateOf("Nome Evento") }
    var location by remember { mutableStateOf("Luogo evento") }
    var organizer by remember { mutableStateOf("Organizzatore") }
    var dateStart by remember { mutableStateOf("Data inizio") }
    var dateEnd by remember { mutableStateOf("Data fine") }
    var dateRegistrationStart by remember { mutableStateOf("Data inizio registrazione") }
    var dateRegistrationEnd by remember { mutableStateOf("Data fine registrazione") }
    var info by remember { mutableStateOf("blablabla") }
    var bookmark by remember { mutableStateOf(false) }

    LaunchedEffect(infoEventState) {
        if (infoEventState !is LoadingOperation) {
            try {
                //infoEventViewModel.get("55000")
            } catch (e: Exception) {
                // Nothing to do
            }
        }
    }

    fun onBookmarkChange(){
        bookmark = !bookmark
    }

    EventricTheme {
        InfoEventContent(
            navController = navController,
            name = name,
            location = location,
            organizer = organizer,
            dateStart = dateStart,
            dateEnd = dateEnd,
            dateRegistrationStart = dateRegistrationStart,
            dateRegistrationEnd = dateRegistrationEnd,
            info = info,
            bookmarked = bookmark,
            onBookmarkChange = ::onBookmarkChange,
        )
    }


}