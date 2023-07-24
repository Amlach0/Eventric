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
import com.eventric.vo.Event

@Composable
fun InfoEventScreen(
    navController: NavController,
    infoEventViewModel: InfoEventViewModel = hiltViewModel()
) {
    val infoEventState by infoEventViewModel.infoEventCodeResult.collectAsState()

    var event: Event? = null
    var name by remember { mutableStateOf("Nome evento") }
    var location by remember { mutableStateOf("Luogo evento") }
    var organizer by remember { mutableStateOf("Organizzatore") }
    var dateStart by remember { mutableStateOf("Data inizio") }
    var dateEnd by remember { mutableStateOf("Data fine") }
    var dateRegistrationStart by remember { mutableStateOf("Data inizio registrazione") }
    var dateRegistrationEnd by remember { mutableStateOf("Data fine registrazione") }
    var info by remember { mutableStateOf("blablabla") }
    var bookmark by remember { mutableStateOf(false) }

    var openRegistration by remember { mutableStateOf(true) }
    var RegistrationText = "Dal "+dateRegistrationStart+" al "+dateRegistrationEnd

    LaunchedEffect(infoEventState) {
        if (infoEventState !is LoadingOperation) {
            try {
                //TODO get all infos
                event = infoEventViewModel.get("W61Dz5reAUvpxuzqJ8rc")
                name = event!!.name.toString()
                location = event!!.location.toString()


                //TODO check if
                if(dateRegistrationEnd>"dataora")
                {
                    openRegistration = false
                    RegistrationText = "Le iscrizioni sono scadute: "+dateRegistrationEnd
                }
                else{
                    if(dateRegistrationStart<"dataora")
                    {
                        openRegistration = false
                        RegistrationText = "Le iscrizioni apriranno: "+dateRegistrationStart
                    }
                    else
                    {
                        openRegistration = true
                        RegistrationText="Hai tempo per iscriverti dal "+dateRegistrationStart+" al "+dateRegistrationEnd
                    }
                }

            } catch (e: Exception) {
                event = infoEventViewModel.get("W61Dz5reAUvpxuzqJ8rc")
                name = event!!.name.toString()
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
            RegistrationText = RegistrationText,
            info = info,
            bookmarked = bookmark,
            openRegistration = openRegistration,
            onBookmarkChange = ::onBookmarkChange,
        )
    }
}