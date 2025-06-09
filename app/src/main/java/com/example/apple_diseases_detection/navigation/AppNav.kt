package com.example.apple_diseases_detection.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apple_diseases_detection.navigation.MainScreens.Camera
import com.example.apple_diseases_detection.navigation.MainScreens.ForgetPassword
import com.example.apple_diseases_detection.navigation.MainScreens.Home
import com.example.apple_diseases_detection.navigation.MainScreens.Login
import com.example.apple_diseases_detection.navigation.MainScreens.Profile
import com.example.apple_diseases_detection.navigation.MainScreens.Register
import com.example.apple_diseases_detection.navigation.MainScreens.Results
import com.example.apple_diseases_detection.navigation.MainScreens.Splash
import com.example.apple_diseases_detection.presentation.screens.auth.forget_password.ForgetPasswordScreen
import com.example.apple_diseases_detection.presentation.screens.auth.login.LoginScreen
import com.example.apple_diseases_detection.presentation.screens.auth.register.RegisterScreen
import com.example.apple_diseases_detection.presentation.screens.detection.DetectionViewModel
import com.example.apple_diseases_detection.presentation.screens.detection.camera.CameraScreen
import com.example.apple_diseases_detection.presentation.screens.detection.result.ResultsScreen
import com.example.apple_diseases_detection.presentation.screens.home.HomeScreen
import com.example.apple_diseases_detection.presentation.screens.profile.ProfileScreen
import com.example.apple_diseases_detection.presentation.screens.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

sealed class MainScreens(val route: String) {
    object Splash : MainScreens("splash_screen")
    object Login : MainScreens("login_screen")
    object Register : MainScreens("register_screen")
    object ForgetPassword : MainScreens("forget_password_screen")
    object Home : MainScreens("home_screen")
    object Camera : MainScreens("camera_screen")
    object Results : MainScreens("result_screen")
    object Profile : MainScreens("profile_screen")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val detectionVM = koinViewModel<DetectionViewModel>()
    NavHost(navController = navController, startDestination = Splash.route) {
        composable(Splash.route) { SplashScreen(navController) }
        composable(Login.route) { LoginScreen(navController) }
        composable(Register.route) { RegisterScreen(navController) }
        composable(ForgetPassword.route) { ForgetPasswordScreen(navController) }
        composable(Home.route) { HomeScreen(navController) }
        composable(Camera.route) { CameraScreen(navController, detectionVM) }
        composable(Results.route) { ResultsScreen(navController, detectionVM) }
        composable(Profile.route) { ProfileScreen(navController) }
    }
}