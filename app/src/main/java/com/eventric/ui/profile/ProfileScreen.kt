package com.eventric.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.eventric.ui.home.HomeContent

@Composable
fun ProfileScreen (
) {

    var name by remember { mutableStateOf("Mario") }
    var surname by remember { mutableStateOf("Rossi") }
    var nFollowed by remember { mutableStateOf(12) }
    var nFollowers by remember { mutableStateOf(15) }
    var bio by remember { mutableStateOf("blablabla") }

    ProfileContent(
        name = name,
        surname = surname,
        nFollowed = nFollowed,
        nFollowers = nFollowers,
        bio = bio,
    )
}