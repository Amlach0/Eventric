package com.eventric.ui.profile

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eventric.ui.theme.EventricTheme
import com.eventric.vo.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen (
    userId: String,
    navController: NavController,
    goToProfile: (String) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    profileViewModel.setUserId(userId)

    val user by profileViewModel.userFlow.collectAsStateWithLifecycle(null)
    val self by profileViewModel.isUserSelfFlow.collectAsStateWithLifecycle(false)
    val isUserFollowed by profileViewModel.isUserFollowedFlow.collectAsStateWithLifecycle(false)
    val followers by profileViewModel.followersFlow.collectAsStateWithLifecycle(null)
    val followed by profileViewModel.followedFlow.collectAsStateWithLifecycle(null)
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded }
    )
    var isFollowersClicked by remember { mutableStateOf(false) }

    fun onFollowChange() = coroutineScope.launch {
        profileViewModel.changeUserFollow(!isUserFollowed)
    }

    fun onEdit(){
        navController.navigate("edit_user?userId=$userId")
    }

    fun onUser(userId: String) = coroutineScope.launch{
        sheetState.hide()
        goToProfile(userId)
    }

    fun onLogout() {
        profileViewModel.logout()
        navController.navigate("login")
    }

    fun showFollow(isFollowers: Boolean) = coroutineScope.launch{
        isFollowersClicked = isFollowers
        sheetState.show()
    }

    EventricTheme {
        ProfileContent(
            navController = navController,
            isFollowersClicked = isFollowersClicked,
            user = user ?: User(),
            self = self,
            isUserFollowed = isUserFollowed,
            followed = followed ?: emptyList(),
            followers = followers ?: emptyList(),
            onFollowClick = ::onFollowChange,
            onEditPress = ::onEdit,
            onLogoutPress = ::onLogout,
            onUser = ::onUser,
            showFollow = ::showFollow,
            sheetState = sheetState,
        )
    }
}