package com.ssitracker.app.ui.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.ui.components.ButtonType
import com.ssitracker.app.ui.components.CustomButton
import com.ssitracker.app.ui.presentation.home.components.DailyTipCard
import com.ssitracker.app.ui.presentation.home.components.HomeHeader
import com.ssitracker.app.ui.presentation.home.components.PillarBreakdown
import com.ssitracker.app.ui.presentation.home.components.SSIScore
import com.ssitracker.app.ui.presentation.home.components.SummaryCards
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToEntry: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState(initial = false)

    HomeScreenContent(
        state = state.value,
        modifier = modifier,
        onNavigateToEntry = onNavigateToEntry,
        onNavigateToHistory = onNavigateToHistory,
        isDarkMode = isDarkMode,
        onToggleTheme = viewModel::toggleTheme
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeState,
    onNavigateToEntry: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {},
) {
    val avgScore = remember(state.ssiList) {
        val scores = state.ssiList?.mapNotNull { it.total } ?: emptyList()
        if (scores.isEmpty()) 0 else scores.average().roundToInt()
    }

    val streak = remember(state.ssiList) {
        val dates = state.ssiList?.mapNotNull { ssi ->
            ssi.createdAt?.let {
                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
            }
        }?.distinct()?.sortedDescending() ?: emptyList()

        if (dates.isEmpty()) return@remember 0

        val today = LocalDate.now()
        val firstDate = dates.first()

        // Se a última entrada não foi hoje nem ontem, a sequência quebrou
        if (firstDate.isBefore(today.minusDays(1))) {
            return@remember 0
        }

        var count = 1
        for (i in 0 until dates.size - 1) {
            if (dates[i].minusDays(1) == dates[i + 1]) {
                count++
            } else {
                break
            }
        }
        count
    }

    val alreadyHasEntryToday = remember(state.ssiList) {
        val today = LocalDate.now()
        state.ssiList?.any { ssi ->
            ssi.createdAt?.let {
                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() == today
            } ?: false
        } ?: false
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            HomeHeader(
                onNavigateToHistory = onNavigateToHistory,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme
            )
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
                DailyTipCard()
            }

            item {
                CustomButton(
                    text = if (alreadyHasEntryToday) "Today's entry added" else "Add Today's Entry",
                    onClick = onNavigateToEntry,
                    type = ButtonType.SECONDARY,
                    enabled = !alreadyHasEntryToday,
                    icon = if (alreadyHasEntryToday) null else Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item {
                SummaryCards(
                    streak = streak,
                    avgScore = avgScore
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
            modifier = Modifier,
            state = HomeState(),
        )
    }
}
