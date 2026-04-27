package com.ssitracker.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssitracker.app.ui.presentation.addentry.AddEntryScreen
import com.ssitracker.app.ui.presentation.history.HistoryScreen
import com.ssitracker.app.ui.presentation.home.HomeScreen
import com.ssitracker.app.ui.presentation.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onFinished = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Home) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screen.Home>{
            HomeScreen(
                onNavigateToEntry = {
                    navController.navigate(Screen.AddEntry)
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History)
                }
            )
        }

        composable<Screen.AddEntry> {
            AddEntryScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Home) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screen.History> {
            HistoryScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
