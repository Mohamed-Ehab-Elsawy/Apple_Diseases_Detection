package com.example.apple_diseases_detection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.apple_diseases_detection.data.local.PreferencesHelper
import com.example.apple_diseases_detection.navigation.AppNavGraph
import com.example.apple_diseases_detection.presentation.components.ui.theme.AppleDiseasesDetectionTheme
import com.example.apple_diseases_detection.presentation.components.ui.theme.white
import com.example.apple_diseases_detection.utils.UserProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    private val preferencesHelper: PreferencesHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserProvider.it = preferencesHelper.fetchUser()
        setContent {
            KoinAndroidContext {
                val navController = rememberNavController()
                AppleDiseasesDetectionTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = white)
                    { AppNavGraph(navController = navController) }
                }
            }
        }
    }
}