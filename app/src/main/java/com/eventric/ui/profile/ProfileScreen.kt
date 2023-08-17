package com.eventric.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.home.HomeContent
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.Event
import com.eventric.vo.User
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ProfileScreen (
    userId: String,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    profileViewModel.setUserId(userId)

    val user by profileViewModel.userFlow.collectAsStateWithLifecycle(null)

    val self by profileViewModel.isUserSelfFlow.collectAsStateWithLifecycle(false)

    val followed by profileViewModel.isUserFollowedFlow.collectAsStateWithLifecycle(false)

    fun onFollowChange() = coroutineScope.launch {
        profileViewModel.changeUserFollow(!followed)
    }

    fun onEditPress(){
        if(self){
            navController.navigate("editprofile")
        }
    }

    EventricTheme {
        ProfileContent(
            navController = navController,
            id = userId,
            user = user ?: User(),
            self = self,
            followed = followed,
            onFollowClick = ::onFollowChange,
            onEditPress = ::onEditPress,
        )
    }
}