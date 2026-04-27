package com.ssitracker.app.ui.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssitracker.app.domain.model.SSI
import com.ssitracker.app.ui.theme.SSITrackerTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

data class SSIChartData(val day: String, val value: Float)

@Composable
fun SSIScore(
    modifier: Modifier = Modifier,
    ssiList: List<SSI> = emptyList()
) {
    val zoneId = ZoneId.systemDefault()
    val today = LocalDate.now(zoneId)
    val oneWeekAgo = today.minusDays(6)

    val filteredList = remember(ssiList) {
        ssiList.filter { ssi ->
            val createdAt = ssi.createdAt ?: return@filter false
            val date = Instant.ofEpochMilli(createdAt).atZone(zoneId).toLocalDate()
            !date.isBefore(oneWeekAgo) && !date.isAfter(today)
        }.sortedBy { it.createdAt }
    }

    val latestSSI = filteredList.lastOrNull()
    val targetScore = latestSSI?.total?.roundToInt() ?: 0

    // Animação do número do Score
    val animatedScore = remember { Animatable(0f) }
    LaunchedEffect(targetScore) {
        animatedScore.animateTo(
            targetValue = targetScore.toFloat(),
            animationSpec = tween(durationMillis = 1500)
        )
    }

    // Cálculo da tendência (último vs primeiro da semana)
    val weekTrend = if (filteredList.size >= 2) {
        (filteredList.last().total ?: 0f) - (filteredList.first().total ?: 0f)
    } else 0f

    // Cálculo da melhoria imediata (último vs anterior)
    val immediateImprovement = if (filteredList.size >= 2) {
        (filteredList.last().total ?: 0f) - (filteredList[filteredList.size - 2].total ?: 0f)
    } else 0f

    val trendColor = when {
        weekTrend > 0 -> Color(0xFF4CAF50)
        weekTrend < 0 -> MaterialTheme.colorScheme.error
        else -> Color.Gray
    }

    val trendIcon = when {
        weekTrend > 0 -> Icons.AutoMirrored.Filled.TrendingUp
        weekTrend < 0 -> Icons.AutoMirrored.Filled.TrendingDown
        else -> Icons.Default.HorizontalRule
    }

    val trendText = when {
        weekTrend > 0 -> "You're improving this week"
        weekTrend < 0 -> "Your score decreased this week"
        else -> "Your score is stable this week"
    }

    val chartData = remember(filteredList) {
        filteredList.map { ssi ->
            val date = Instant.ofEpochMilli(ssi.createdAt!!).atZone(zoneId).toLocalDate()
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
            SSIChartData(dayName, ssi.total ?: 0f)
        }
    }

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
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Your SSI Score",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                if (filteredList.size >= 2) {
                    val badgeColor =
                        if (immediateImprovement >= 0) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(badgeColor.copy(alpha = 0.1f))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (immediateImprovement >= 0) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
                            contentDescription = null,
                            tint = badgeColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${if (immediateImprovement >= 0) "+" else ""}${immediateImprovement.toInt()}",
                            style = MaterialTheme.typography.labelLarge,
                            color = badgeColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${animatedScore.value.toInt()}",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.alignByBaseline()
                )
                Text(
                    text = " /100",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.alignByBaseline()
                )
            }

            if (filteredList.size >= 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = trendIcon,
                        contentDescription = null,
                        tint = trendColor,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = trendText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = trendColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SSIChart(
                data = chartData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun SSIChart(
    data: List<SSIChartData>,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val axisColor = Color.Gray.copy(alpha = 0.5f)
    val gridColor = Color.LightGray.copy(alpha = 0.3f)

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
    }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val paddingLeft = 40.dp.toPx()
        val paddingRight = 16.dp.toPx()
        val paddingBottom = 30.dp.toPx()
        val chartWidth = width - paddingLeft - paddingRight
        val chartHeight = height - paddingBottom

        val minVal = 0f
        val maxVal = 100f
        val range = maxVal - minVal

        // Grid Horizontal
        val gridSteps = 5
        for (i in 0..gridSteps) {
            val labelValue = (minVal + (i * 20)).toInt()
            val y = chartHeight - (i * (chartHeight / gridSteps))

            drawLine(
                color = gridColor,
                start = Offset(paddingLeft, y),
                end = Offset(paddingLeft + chartWidth, y),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            drawContext.canvas.nativeCanvas.drawText(
                labelValue.toString(),
                10f,
                y + 10f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 12.dp.toPx()
                    textAlign = android.graphics.Paint.Align.LEFT
                }
            )
        }

        // Eixos
        drawLine(color = axisColor, start = Offset(paddingLeft, 0f), end = Offset(paddingLeft, chartHeight), strokeWidth = 1.dp.toPx())
        drawLine(color = axisColor, start = Offset(paddingLeft, chartHeight), end = Offset(paddingLeft + chartWidth, chartHeight), strokeWidth = 1.dp.toPx())

        if (data.isNotEmpty()) {
            val spaceBetweenPoints = if (data.size > 1) chartWidth / (data.size - 1) else 0f

            val points = data.mapIndexed { index, ssi ->
                val x = paddingLeft + (index * spaceBetweenPoints)
                val y = chartHeight - ((ssi.value - minVal).coerceIn(0f, 100f) / range * chartHeight)
                Offset(x, y)
            }

            clipRect(
                left = 0f,
                top = 0f,
                right = paddingLeft + (chartWidth * animationProgress.value) + 10.dp.toPx(),
                bottom = height
            ) {
                if (points.size > 1) {
                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }
                    drawPath(path = path, color = primaryColor, style = Stroke(width = 3.dp.toPx()))
                }

                points.forEachIndexed { index, offset ->
                    drawCircle(color = primaryColor, radius = 5.dp.toPx(), center = offset)
                    drawCircle(color = Color.White, radius = 2.dp.toPx(), center = offset)

                    drawContext.canvas.nativeCanvas.drawText(
                        data[index].day,
                        offset.x - 15f,
                        height,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.GRAY
                            textSize = 10.dp.toPx()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SSIScorePreview() {
    val now = System.currentTimeMillis()
    val dayMillis = 24 * 60 * 60 * 1000L

    val fakeSSI = listOf(
        SSI(total = 65f, createdAt = now - 6 * dayMillis),
        SSI(total = 42f, createdAt = now - 5 * dayMillis),
        SSI(total = 81f, createdAt = now - 4 * dayMillis),
        SSI(total = 49f, createdAt = now - 3 * dayMillis),
        SSI(total = 65f, createdAt = now - 2 * dayMillis),
        SSI(total = 72f, createdAt = now - 1 * dayMillis),
        SSI(total = 79f, createdAt = now)
    )

    SSITrackerTheme(darkTheme = false) {
        SSIScore(ssiList = fakeSSI)
    }
}