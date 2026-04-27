package com.ssitracker.app.ui.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.ui.theme.SSITrackerTheme
import com.ssitracker.app.util.DateUtils
import java.time.LocalDate

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onNavigateToHistory: () -> Unit = {},
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember(currentDate) {
        DateUtils.formatToFullDate(currentDate)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }



        IconButton(onClick = onToggleTheme) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        IconButton(onClick = onNavigateToHistory) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    SSITrackerTheme(darkTheme = false) {
        HomeHeader()
    }
}
