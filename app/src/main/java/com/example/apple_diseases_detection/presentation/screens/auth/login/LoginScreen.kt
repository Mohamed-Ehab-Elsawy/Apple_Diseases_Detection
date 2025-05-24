package com.example.apple_diseases_detection.presentation.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.navigation.MainScreens
import com.example.apple_diseases_detection.presentation.components.ui.AppTopBar
import com.example.apple_diseases_detection.presentation.components.ui.CustomButton
import com.example.apple_diseases_detection.presentation.components.ui.EmailTextField
import com.example.apple_diseases_detection.presentation.components.ui.PasswordTextField
import com.example.apple_diseases_detection.presentation.components.ui.ProgressDialog
import com.example.apple_diseases_detection.presentation.components.ui.theme.errorContainer
import com.example.apple_diseases_detection.presentation.components.ui.theme.onErrorContainer
import com.example.apple_diseases_detection.presentation.components.ui.theme.secondary
import com.example.apple_diseases_detection.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = koinViewModel()) {

    val uiState by vm.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()
        is UiState.Success -> LaunchedEffect(Unit) { navController.navigate(MainScreens.Home.route) }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                val message = (uiState as UiState.Error).t.localizedMessage

                coroutineScope.launch {
                    if (message != null) {
                        snackBarHostState.showSnackbar(
                            message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.login)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = errorContainer,
                    contentColor = onErrorContainer
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                EmailTextField(
                    value = vm.email.value,
                    onValueChange = vm::onEmailChange,
                    errorMessage = vm.emailError.value
                )
                PasswordTextField(
                    value = vm.password.value,
                    onValueChange = vm::onPasswordChange,
                    errorMessage = vm.passwordError.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                )

                Text(
                    text = stringResource(R.string.forget_password),
                    color = secondary,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            //navController.navigate(MainScreens.ForgetPassword.route)
                        }
                        .align(Alignment.Start)
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                CustomButton(
                    text = stringResource(R.string.login),
                    enabled = uiState !is UiState.Loading,
                    onClick = {
                        keyboardController?.hide()
                        vm.login()
                    }
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                Text(
                    text = stringResource(R.string.create_new_account),
                    color = secondary,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { navController.navigate(MainScreens.Register.route) }
                        .align(Alignment.Start)
                )
            }
        }
    }
}