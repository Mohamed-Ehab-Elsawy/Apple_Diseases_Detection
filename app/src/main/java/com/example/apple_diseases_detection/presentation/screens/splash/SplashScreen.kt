package com.example.apple_diseases_detection.presentation.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.navigation.MainScreens
import com.example.apple_diseases_detection.presentation.components.ui.theme.white
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }

    val rotationAnim by animateFloatAsState(
        targetValue = if (isVisible) 360f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "rotate"
    )
    val alphaAnim by animateFloatAsState(
        targetValue = when {
            isVisible && rotationAnim < 360f -> 0.5f
            isVisible -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 1200),
        label = "alpha"
    )
    val scaleAnim by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "scale"
    )
    var startFadeOut by remember { mutableStateOf(false) }
    val fadeOutAlpha by animateFloatAsState(
        targetValue = if (startFadeOut) 0f else 1f,
        animationSpec = tween(durationMillis = 400),
        label = "fadeOut"
    )

    LaunchedEffect(Unit) {
        isVisible = true
        delay(1800)

        startFadeOut = true
        delay(400)

        navController.navigate(MainScreens.Login.route)
    }
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(white),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.4f)
                    .scale(scaleAnim)
                    .alpha(alphaAnim * fadeOutAlpha)
                    .graphicsLayer {
                        rotationY = rotationAnim
                        cameraDistance = 8 * density
                    }
            )
        }
    }
}