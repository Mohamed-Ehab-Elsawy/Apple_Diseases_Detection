package com.example.apple_diseases_detection.presentation.screens.auth.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.apple_diseases_detection.presentation.components.ui.theme.primary
import com.example.apple_diseases_detection.presentation.screens.auth.register.component.NameTextField
import com.example.apple_diseases_detection.presentation.screens.auth.register.component.PhoneTextField
import com.example.apple_diseases_detection.utils.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavController, vm: SignupViewModel = koinViewModel()
) {

    val uiState by vm.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val autoFillManager = LocalAutofillManager.current

    when (uiState) {
        is UiState.Idle -> {}
        is UiState.Loading -> ProgressDialog()
        is UiState.Success -> LaunchedEffect(Unit) { navController.navigate(MainScreens.Home.route) }

        is UiState.Error -> {
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        (uiState as UiState.Error).t.localizedMessage ?: "Error",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.register),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                iconTint = primary,
                onIconClick = { navController.popBackStack() }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    NameTextField(
                        value = vm.firstName.value,
                        onValueChange = { vm.onFirstNameChange(it) },
                        label = stringResource(R.string.first_name),
                        isError = vm.firstNameError.value != null,
                        errorText = vm.firstNameError.value ?: ""
                    )
                }
                item {
                    NameTextField(
                        value = vm.lastName.value,
                        onValueChange = { vm.onLastNameChange(it) },
                        label = stringResource(R.string.last_name),
                        isError = vm.lastNameError.value != null,
                        errorText = vm.lastNameError.value ?: ""
                    )
                }

                item {
                    EmailTextField(
                        value = vm.email.value,
                        onValueChange = { vm.onEmailChange(it) },
                        errorMessage = vm.emailError.value
                    )
                }
                item {
                    PhoneTextField(
                        value = vm.phoneNumber.value,
                        onValueChange = { vm.onPhoneNumberChange(it) },
                        isError = vm.phoneNumberError.value != null,
                        errorText = vm.phoneNumberError.value ?: ""
                    )
                }

                item {
                    PasswordTextField(
                        value = vm.password.value,
                        onValueChange = { vm.onPasswordChange(it) },
                        errorMessage = vm.passwordError.value
                    )
                }

                // Confirm password
                item {
                    PasswordTextField(
                        value = vm.confirmPassword.value,
                        onValueChange = { vm.onConfirmPasswordChange(it) },
                        label = stringResource(id = R.string.confirm_your_password),
                        errorMessage = vm.confirmPasswordError.value
                    )
                }

                item {
                    CustomButton(
                        modifier = Modifier.padding(top = 24.dp),
                        text = stringResource(id = R.string.register),
                        enabled = uiState !is UiState.Loading,
                        onClick = {
                            keyboardController?.hide()
                            autoFillManager?.commit()
                            vm.signup()
                        }
                    )
                }
            }
        }
    }

}