package com.ssitracker.app.ui.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssitracker.app.ui.theme.SSITrackerTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {}
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        delay(1500)
        onFinished()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale.value)
                .alpha(alpha.value)
                .background(Color.White.copy(0.1f), shape = RoundedCornerShape(24.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                contentDescription = "App Logo",
                tint = Color.White,
                modifier = Modifier
                    .padding(12.dp)
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SSI Tracker",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.alpha(alpha.value)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SSITrackerTheme(darkTheme = false) {
        SplashScreen()
    }
}
