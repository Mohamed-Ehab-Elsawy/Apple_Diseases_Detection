package com.example.apple_diseases_detection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.apple_diseases_detection.navigation.AppNavGraph
import com.example.apple_diseases_detection.presentation.components.ui.theme.AppleDiseasesDetectionTheme
import com.example.apple_diseases_detection.presentation.components.ui.theme.white
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            KoinAndroidContext {
                val navController = rememberNavController()
                AppleDiseasesDetectionTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = white
                    )
                    { AppNavGraph(navController = navController) }
                }
            }
        }
    }
}