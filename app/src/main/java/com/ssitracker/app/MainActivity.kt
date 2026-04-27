package com.ssitracker.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.ssitracker.app.data.local.datastore.ThemeManager
import com.ssitracker.app.ui.navigation.NavGraph
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val themeManager: ThemeManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        setContent {
            val isDarkMode by themeManager.isDarkMode.collectAsState(initial = false)
            val navController = rememberNavController()

            SSITrackerTheme(darkTheme = isDarkMode) {
                NavGraph(navController = navController)
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}


