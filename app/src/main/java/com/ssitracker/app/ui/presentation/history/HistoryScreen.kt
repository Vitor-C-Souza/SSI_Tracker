package com.ssitracker.app.ui.presentation.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.ui.components.CustomTopAppBar
import com.ssitracker.app.ui.presentation.history.components.AVGScore
import com.ssitracker.app.ui.presentation.history.components.SSICard
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = koinViewModel(),
    onBack: () -> Unit = {},
) {
    val state = viewModel.state.collectAsState()

    HistoryScreenContent(
        state = state.value,
        ssiList = state.value.history ?: emptyList(),
        modifier = modifier,
        onBack = onBack
    )

}

@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    state: HistoryState = HistoryState(),
    onBack: () -> Unit = {},
    ssiList: List<SSI>
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                label = "History",
                onBack = onBack
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            item {
                AVGScore(avgScore = state.avgScore)
            }

            items(ssiList) { ssi ->
                SSICard(ssi = ssi)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    val now = System.currentTimeMillis()
    val dayMillis = 24 * 60 * 60 * 1000L
    SSITrackerTheme(darkTheme = false) {
        HistoryScreenContent(
            ssiList = listOf(
                SSI(
                    total = 75f,
                    createdAt = now,
                ),
                SSI(
                    total = 69f,
                    createdAt = now - 1 * dayMillis,
                ),
                SSI(
                    total = 72f,
                    createdAt = now - 2 * dayMillis,
                ),
            ),
            state = HistoryState(
                avgScore = 70
            )
        )
    }
}