package com.eventric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eventric.ui.auth.login.LoginScreen
import com.eventric.ui.dispatcher.DispatcherScreen
import com.eventric.ui.home.HomeScreen
import com.eventric.ui.newEvent.CreateEventScreen
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
                            onSuccess = { navController.navigate("dispatcher") { popUpTo(0) } }
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
                            mainNavController = navController
                        )
                    }
                    composable("new_event") {
                      CreateEventScreen(
                          navController = navController,
                          onSubmit = { navController.navigate("home") { popUpTo(0) } }
                      )
                    }
                }
            }
        }
    }
}
