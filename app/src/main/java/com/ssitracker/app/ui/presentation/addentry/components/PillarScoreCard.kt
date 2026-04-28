package com.ssitracker.app.ui.presentation.addentry.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.ui.theme.SSITrackerTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillarScoreCard(
    title: String,
    score: Float,
    onScoreChange: (Float) -> Unit,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val textFieldValue = remember { mutableStateOf(String.format(Locale.US, "%.3f", score)) }

    LaunchedEffect(score) {
        textFieldValue.value = String.format(Locale.US, "%.3f", score)
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
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color.copy(alpha = 0.1f), shape = CircleShape)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))


                Column(modifier = Modifier.weight(3f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Score 0–25",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Column(
                    modifier = Modifier.weight(3f),
                    horizontalAlignment = Alignment.End
                ) {
                    BasicTextField(
                        value = textFieldValue.value,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.matches(Regex("""^\d*\.?\d*$"""))) {
                                textFieldValue.value = newValue
                                val numValue = newValue.toFloatOrNull()
                                if (numValue != null && numValue in 0f..25f) {
                                    onScoreChange(numValue)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = color,
                            textAlign = TextAlign.End
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        cursorBrush = SolidColor(color)
                    )
                    Text(
                        text = "/ 25",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Slider(
                value = score,
                onValueChange = onScoreChange,
                valueRange = 0f..25f,
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = remember { MutableInteractionSource() },
                        colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.size(16.dp)
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        colors = SliderDefaults.colors(
                            activeTrackColor = color,
                            inactiveTrackColor = Color(0xFFF1F5F9)
                        ),
                        sliderState = sliderState,
                        modifier = Modifier.height(6.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "0",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "25",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PillarScoreCardPreview() {
    SSITrackerTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            PillarScoreCard(
                title = "Professional Brand",
                score = 12.5f,
                onScoreChange = {},
                icon = Icons.Outlined.WorkOutline,
                color = Color(0xFF4CAF50)
            )
        }
    }
}
