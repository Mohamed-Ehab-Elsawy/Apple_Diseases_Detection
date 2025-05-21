package com.example.apple_diseases_detection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.apple_diseases_detection.navigation.AppNavGraph
import com.example.apple_diseases_detection.presentation.components.ui.theme.AppleDiseasesDetectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppleDiseasesDetectionTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}