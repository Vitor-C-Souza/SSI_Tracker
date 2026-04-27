package com.ssitracker.app.ui.presentation.addentry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.ui.components.CustomButton
import com.ssitracker.app.ui.components.CustomTopAppBar
import com.ssitracker.app.ui.presentation.addentry.components.Date
import com.ssitracker.app.ui.presentation.addentry.components.OverallSSIScore
import com.ssitracker.app.ui.presentation.addentry.components.PillarScoreCard
import com.ssitracker.app.ui.theme.SSITrackerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: AddEntryViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(state.value.isSuccessful) {
        if (state.value.isSuccessful) {
            onNavigateToHome()
        }
    }

    AddEntryScreenContent(
        state = state.value,
        onEvent = viewModel::onEvent,
        modifier = modifier,
        onBack = onBack,
        onNavigateToHome = onNavigateToHome
    )

}

@Composable
fun AddEntryScreenContent(
    modifier: Modifier = Modifier,
    state: AddEntryState = AddEntryState(),
    onEvent: (AddEntryEvent) -> Unit = {},
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                label = "Add Entry",
                onBack = onBack
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(it)
        ) {
            item {
                Date()
            }
            item {
                OverallSSIScore(
                    score = state.total ?: 0f,
                    onScoreChange = { newScore ->
                        onEvent(AddEntryEvent.TotalChanged(newScore.toString()))
                    }
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Four Pillars",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B2A)
                    )
                    Text(
                        text = "Values based on LinkedIn SSI breakdown",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF0D1B2A).copy(alpha = 0.6f)
                    )
                }
            }

            item {
                PillarScoreCard(
                    title = "Professional Brand",
                    score = state.professionalBrand ?: 0f,
                    onScoreChange = { professionalBrand ->
                        onEvent(
                            AddEntryEvent.ProfessionalBrandChanged(
                                professionalBrand.toString()
                            )
                        )
                    },
                    icon = Icons.Outlined.WorkOutline,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                PillarScoreCard(
                    title = "Find the Right People",
                    score = state.findPeople ?: 0f,
                    onScoreChange = { findPeople ->
                        onEvent(
                            AddEntryEvent.FindPeopleChanged(
                                findPeople.toString()
                            )
                        )
                    },
                    icon = Icons.Outlined.PersonOutline,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            item {
                PillarScoreCard(
                    title = "Engage with Insights",
                    score = state.engageInsights ?: 0f,
                    onScoreChange = { engageInsights ->
                        onEvent(
                            AddEntryEvent.EngageInsightsChanged(
                                engageInsights.toString()
                            )
                        )
                    },
                    icon = Icons.Outlined.ChatBubbleOutline,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            item {
                PillarScoreCard(
                    title = "Build Relationships",
                    score = state.buildRelationships ?: 0f,
                    onScoreChange = { buildRelationships ->
                        onEvent(
                            AddEntryEvent.BuildRelationshipsChanged(
                                buildRelationships.toString()
                            )
                        )
                    },
                    icon = Icons.Outlined.Hub,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            }

            item {
                CustomButton(
                    text = "Save Entry",
                    onClick = { onEvent(AddEntryEvent.SaveEntry) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddEntryScreenPreview() {
    SSITrackerTheme(darkTheme = false) {
        AddEntryScreenContent()
    }
}
