package com.example.apple_diseases_detection.presentation.screens.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apple_diseases_detection.R
import com.example.apple_diseases_detection.presentation.components.ui.theme.onPrimary
import com.example.apple_diseases_detection.presentation.components.ui.theme.primary

@Composable
fun ShutterButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 16.dp)
            .size(64.dp)
            .background(primary, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_camera_ai),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = onPrimary
        )
    }
}