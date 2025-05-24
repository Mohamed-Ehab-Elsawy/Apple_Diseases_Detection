package com.example.apple_diseases_detection.presentation.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apple_diseases_detection.presentation.components.ui.theme.AppTypography
import com.example.apple_diseases_detection.presentation.components.ui.theme.primary
import com.example.apple_diseases_detection.presentation.components.ui.theme.transparent
import com.example.apple_diseases_detection.presentation.components.ui.theme.white

@Composable
fun AppTopBar(
    title: String,
    icon: ImageVector? = null,
    iconTint: Color? = null,
    onIconClick: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        color = white
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 36.dp)
                .fillMaxWidth()
                .background(transparent)
        ) {
            Text(
                text = title,
                style = AppTypography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = primary,
                modifier = Modifier.align(Alignment.Center)
            )
            if (icon != null && iconTint != null && onIconClick != null)
                IconButton(
                    onClick = onIconClick,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(32.dp)
                    )
                }
        }
    }
}