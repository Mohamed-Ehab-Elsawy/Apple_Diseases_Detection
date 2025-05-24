package com.example.apple_diseases_detection.presentation.screens.auth.register.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.apple_diseases_detection.presentation.components.ui.theme.error

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        maxLines = 1,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        isError = isError,
        supportingText = {
            Text(errorText, color = error)
        }
    )
}