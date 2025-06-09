package com.example.apple_diseases_detection.presentation.screens.detection.result

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.navigation.NavController
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.navigation.MainScreens
import com.example.apple_diseases_detection.presentation.components.ui.AppTopBar
import com.example.apple_diseases_detection.presentation.components.ui.CustomButton
import com.example.apple_diseases_detection.presentation.components.ui.ProgressDialog
import com.example.apple_diseases_detection.presentation.components.ui.theme.primary
import com.example.apple_diseases_detection.presentation.screens.detection.DetectionViewModel
import com.example.apple_diseases_detection.utils.UiState

@Composable
fun ResultsScreen(
    navController: NavController,
    vm: DetectionViewModel
) {
    val uiState by vm.uiState.collectAsState()
    var image by remember {
        mutableStateOf<Bitmap>(createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
    var text by remember { mutableStateOf<String>("") }

    Scaffold(
        topBar = { AppTopBar(title = stringResource(R.string.results)) }
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
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(top = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = primary,
                )
            }
            CustomButton(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                text = "Go Home",
                onClick = {
                    navController.navigate(MainScreens.Home.route) {
                        popUpTo(MainScreens.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> ProgressDialog()
            is UiState.Success -> {
                image = vm.image ?: createBitmap(0, 0, Bitmap.Config.ARGB_8888)
                text = (uiState as UiState.Success<String>).data
            }

            is UiState.Error -> {}
        }
    }
}