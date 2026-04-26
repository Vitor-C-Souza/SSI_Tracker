package com.ssitracker.app.ui.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssitracker.app.ui.presentation.home.components.HomeHeader
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()

    HomeScreenContent(
        state = state.value,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeState,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            HomeHeader()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SSITrackerTheme(darkTheme = false) {
        HomeScreenContent(
            state = HomeState(),
            modifier = Modifier
        )
    }
}