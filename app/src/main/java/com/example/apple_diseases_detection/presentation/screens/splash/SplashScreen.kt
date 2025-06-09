package com.example.apple_diseases_detection.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.navigation.MainScreens
import com.example.apple_diseases_detection.presentation.components.ui.theme.white
import com.example.apple_diseases_detection.utils.UserProvider
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000)

        if (UserProvider.it.id == null)
            navController.navigate(MainScreens.Login.route) {
                popUpTo(MainScreens.Splash.route) {
                    inclusive = true
                }
            }
        else
            navController.navigate(MainScreens.Home.route) {
                popUpTo(MainScreens.Splash.route) {
                    inclusive = true
                }
            }
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
            )
        }
    }

}