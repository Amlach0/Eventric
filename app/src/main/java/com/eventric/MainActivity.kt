package com.eventric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eventric.ui.auth.login.LoginScreen
import com.eventric.ui.auth.signup.SignupScreen
import com.eventric.ui.detailEvent.DetailEventScreen
import com.eventric.ui.dispatcher.DispatcherScreen
import com.eventric.ui.events.EventsScreen
import com.eventric.ui.home.HomeScreen
import com.eventric.ui.newEvent.CreateEventScreen
import com.eventric.ui.profile.ProfileScreen
import com.eventric.ui.notifications.NotificationsScreen
import com.eventric.ui.theme.EventricTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            EventricTheme {
                NavHost(
                    navController = navController,
                    startDestination = "dispatcher"
                ) {
                    composable("login") {
                        LoginScreen(
                            onSuccess = { navController.navigate("dispatcher") { popUpTo(0) } },
                            goToSignup = { navController.navigate("signup") { popUpTo(0) } }
                        )
                    }
                    composable("signup"){
                        SignupScreen(
                            goToDispatcher = { navController.navigate("dispatcher") { popUpTo(0) } },
                        )
                    }
                    composable("dispatcher") {
                        DispatcherScreen(
                            goToHome = { navController.navigate("home") { popUpTo(0) } },
                            goToLogin = { navController.navigate("login") { popUpTo(0) } }
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            mainNavController = navController,
                        )
                    }
                    composable("new_event") {
                        CreateEventScreen(
                            navControllerForBack = navController,
                            onSuccess = { eventId -> navController.navigate("info_event?eventId=$eventId") }
                        )
                    }
                    composable("edit_event?eventId={eventId}") { navBackStackEntry ->
                        CreateEventScreen(
                            id = navBackStackEntry.arguments?.getString("eventId")
                                ?: throw IllegalStateException("missing event id arguments"),
                            navControllerForBack = navController,
                            onSuccess = { eventId ->
                                navController.popBackStack()
                                navController.navigate("info_event?eventId=$eventId") {
                                    launchSingleTop = true
                                }
                            },
                            onDelete = { navController.navigate("home") { popUpTo(0) } }
                        )
                    }
                    composable("info_event?eventId={eventId}") { navBackStackEntry ->
                        val eventId = navBackStackEntry.arguments?.getString("eventId")
                            ?: throw IllegalStateException("missing event id arguments")
                        DetailEventScreen(
                            eventId = eventId,
                            navControllerForBack = navController,
                            goToEditEvent = { navController.navigate("edit_event?eventId=$eventId") },
                            goToProfile = { userId -> navController.navigate("profile?userId=$userId") }
                        )
                    }
                    composable("edit_user?userId={userId}") { navBackStackEntry ->
                        SignupScreen(
                            id = navBackStackEntry.arguments?.getString("userId")
                                ?: throw IllegalStateException("missing user id arguments"),
                            goToDispatcher = { navController.navigate("dispatcher") { popUpTo(0) } },
                        )
                    }
                    composable("profile?userId={userId}") { navBackStackEntry ->
                        val userId = navBackStackEntry.arguments?.getString("userId")
                            ?: throw IllegalStateException("missing user id arguments")
                        ProfileScreen(
                            userId = userId,
                            navController = navController,
                            goToProfile = { goUserId -> navController.navigate("profile?userId=$goUserId") },
                            goToEvent = { eventId -> navController.navigate("info_event?eventId=$eventId") },
                            goToEditProfile = {},
                            goToDispatcher = {}
                        )
                    }
                    composable("notifications") {
                        NotificationsScreen(
                            navControllerForBack = navController,
                            goToEvent = { eventId -> navController.navigate("info_event?eventId=$eventId") },
                            goToUser = {  }, //TODO add user page
                        )
                    }
                    composable("events") {
                        EventsScreen(
                            goToEventDetail = { navController.navigate("info_event?eventId=$it") },
                        )
                    }
                }
            }
        }
    }
}
