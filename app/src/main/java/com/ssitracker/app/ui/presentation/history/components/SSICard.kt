package com.ssitracker.app.ui.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.ui.theme.SSITrackerTheme
import com.ssitracker.app.util.DateUtils
import java.time.Instant
import java.time.ZoneId

@Composable
fun SSICard(
    modifier: Modifier = Modifier,
    ssi: SSI
) {
    val date = remember(ssi.createdAt) {
        ssi.createdAt?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }

    val relativeLabel = remember(date) {
        date?.let { DateUtils.getRelativeDayLabel(it) } ?: "N/A"
    }

    val formattedDate = remember(date) {
        date?.let { DateUtils.formatToFullDateWithoutDayOfWeek(it) } ?: "N/A"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = relativeLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Text(
                text = ssi.total?.toInt()?.toString() ?: "N/A",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SSICardPreview() {
    SSITrackerTheme(darkTheme = false) {
        SSICard(
            ssi = SSI(
                total = 75f,
                createdAt = System.currentTimeMillis(),
            )
        )
    }
}
