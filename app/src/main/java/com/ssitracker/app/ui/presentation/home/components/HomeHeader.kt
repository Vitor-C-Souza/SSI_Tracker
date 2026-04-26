package com.ssitracker.app.ui.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun HomeHeaderPreview() {
    SSITrackerTheme(darkTheme = false) {
        HomeHeader()
    }
}
