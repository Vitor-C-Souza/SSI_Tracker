package com.ssitracker.app.ui.presentation.home

import android.content.ClipData
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.ui.presentation.home.components.HomeHeader
import com.ssitracker.app.ui.presentation.home.components.PillarBreakdown
import com.ssitracker.app.ui.presentation.home.components.SSIScore
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.androidx.compose.koinViewModel

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

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeState,
) {
    val latestSSI = state.ssiList?.maxByOrNull { it.createdAt ?: 0L }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            HomeHeader()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                SSIScore(
                    ssiList = state.ssiList ?: emptyList()
                )
            }

            item {
                PillarBreakdown(
                    ssiList = state.ssiList ?: emptyList()
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

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