package com.example.apple_diseases_detection.presentation.screens.auth.forget_password

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.presentation.components.ui.AppTopBar
import com.example.apple_diseases_detection.presentation.components.ui.CustomButton
import com.example.apple_diseases_detection.presentation.components.ui.EmailTextField
import com.example.apple_diseases_detection.presentation.components.ui.ProgressDialog
import com.example.apple_diseases_detection.presentation.components.ui.theme.primary
import com.example.apple_diseases_detection.utils.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    viewModel: ForgetPasswordViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.forget_password),
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                iconTint = primary,
                onIconClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.padding(top = 50.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp),
                        tint = primary
                    )
                }

                Spacer(modifier = Modifier.padding(top = 50.dp))

                EmailTextField(
                    value = viewModel.email.value,
                    onValueChange = viewModel::onEmailChange,
                    errorMessage = viewModel.emailError.value
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                CustomButton(
                    text = stringResource(R.string.send_reset_email),
                    enabled = uiState !is UiState.Loading,
                    onClick = {
                        keyboardController?.hide()
                        viewModel.sendResetPasswordEmail()
                    }
                )
            }
        }

        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> ProgressDialog()
            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.password_reset_email_sent),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }
            }

            is UiState.Error -> {
                LaunchedEffect(Unit) {
                    val errorMessage = (uiState as UiState.Error).t.localizedMessage
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}