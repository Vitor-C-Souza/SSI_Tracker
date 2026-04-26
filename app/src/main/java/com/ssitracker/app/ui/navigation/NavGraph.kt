package com.ssitracker.app.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssitracker.app.ui.presentation.home.HomeScreen
import com.ssitracker.app.ui.presentation.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            HomeScreen()
        }
    }
}
