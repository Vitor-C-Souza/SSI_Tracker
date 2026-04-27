package com.ssitracker.app.ui.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.ui.theme.SSITrackerTheme
import java.util.Locale

@Composable
fun PillarBreakdown(
    modifier: Modifier = Modifier,
    ssiList: List<SSI> = emptyList()
) {
    val sortedList = remember(ssiList) {
        ssiList.sortedByDescending { it.createdAt ?: 0L }
    }

    val latest = sortedList.getOrNull(0)
    val secondLatest = sortedList.getOrNull(1)

    // Lógica para encontrar o melhor pilar e o que precisa de atenção
    val pillars = remember(latest) {
        latest?.let {
            listOf(
                "Professional Brand" to (it.professionalBrand ?: 0f),
                "Find the right people" to (it.findPeople ?: 0f),
                "Engage with insights" to (it.engageInsights ?: 0f),
                "Build relationships" to (it.buildRelationships ?: 0f)
            )
        } ?: emptyList()
    }

    val bestPillar = pillars.maxByOrNull { it.second }?.first ?: "N/A"
    val needsAttentionPillar = pillars.minByOrNull { it.second }?.first ?: "N/A"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Pillar Breakdown",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            // Professional Brand
            PillarItem(
                label = "Professional\nBrand",
                targetValue = latest?.professionalBrand ?: 0f,
                improvement = (latest?.professionalBrand ?: 0f) - (secondLatest?.professionalBrand ?: 0f),
                icon = Icons.Outlined.WorkOutline,
                color = MaterialTheme.colorScheme.primary
            )

            // Find People
            PillarItem(
                label = "Find the right\npeople",
                targetValue = latest?.findPeople ?: 0f,
                improvement = (latest?.findPeople ?: 0f) - (secondLatest?.findPeople ?: 0f),
                icon = Icons.Outlined.PersonOutline,
                color = MaterialTheme.colorScheme.secondary
            )

            // Engage Insights
            PillarItem(
                label = "Engage with\ninsights",
                targetValue = latest?.engageInsights ?: 0f,
                improvement = (latest?.engageInsights ?: 0f) - (secondLatest?.engageInsights ?: 0f),
                icon = Icons.Outlined.ChatBubbleOutline,
                color = MaterialTheme.colorScheme.tertiary
            )

            // Build Relationships
            PillarItem(
                label = "Build\nrelationships",
                targetValue = latest?.buildRelationships ?: 0f,
                improvement = (latest?.buildRelationships ?: 0f) - (secondLatest?.buildRelationships ?: 0f),
                icon = Icons.Outlined.Hub,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Insights Section com animação de fade-in
            val alpha = remember { Animatable(0f) }
            LaunchedEffect(bestPillar) {
                alpha.animateTo(1f, animationSpec = tween(1000, delayMillis = 500))
            }

            Column(
                modifier = Modifier.alpha(alpha.value),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PillarInsightItem(
                    dotColor = MaterialTheme.colorScheme.primary,
                    label = "Best pillar",
                    value = bestPillar
                )
                PillarInsightItem(
                    dotColor = MaterialTheme.colorScheme.secondary,
                    label = "Needs attention",
                    value = needsAttentionPillar
                )
            }
        }
    }
}

@Composable
fun PillarInsightItem(
    dotColor: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(8.dp)
                .background(dotColor, shape = CircleShape)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black.copy(alpha = 0.8f))) {
                    append("$label: ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color.Black.copy(alpha = 0.6f))) {
                    append(value)
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun PillarItem(
    label: String,
    targetValue: Float,
    improvement: Float,
    icon: ImageVector,
    color: Color
) {
    val animatedValue = remember { Animatable(0f) }

    LaunchedEffect(targetValue) {
        animatedValue.animateTo(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 1500)
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 18.sp,
                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (improvement >= 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    imageVector = if (improvement >= 0) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
                    contentDescription = null,
                    tint = if (improvement >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${if (improvement >= 0) "+" else ""}${
                        String.format(
                            Locale.US,
                            "%.1f",
                            improvement
                        )
                    }",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (improvement >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = String.format(Locale.US, "%.1f", animatedValue.value),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "/25",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }

        LinearProgressIndicator(
            progress = { animatedValue.value / 25f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = color,
            trackColor = color.copy(alpha = 0.1f),
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PillarBreakdownPreview() {
    val now = System.currentTimeMillis()
    val fakeSSI = listOf(
        SSI(
            professionalBrand = 18.7f,
            findPeople = 12.5f,
            engageInsights = 15.2f,
            buildRelationships = 21.0f,
            createdAt = now
        ),
        SSI(
            professionalBrand = 17.5f,
            findPeople = 13.0f,
            engageInsights = 15.6f,
            buildRelationships = 18.9f,
            createdAt = now - 86400000 // Ontem
        )
    )
    SSITrackerTheme(darkTheme = false) {
        PillarBreakdown(ssiList = fakeSSI)
    }
}
