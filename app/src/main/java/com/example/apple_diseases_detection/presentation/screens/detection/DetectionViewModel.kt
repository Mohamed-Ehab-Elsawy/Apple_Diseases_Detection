package com.example.apple_diseases_detection.presentation.screens.detection

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apple_diseases_detection.data.remote.repository.DetectionRepository
import com.example.apple_diseases_detection.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetectionViewModel(
    private val repo: DetectionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val uiState: StateFlow<UiState<String>> = _uiState

    var image: Bitmap? = null
        private set

    fun analyzeImageWithGemini(imageBitmap: Bitmap) {
        try {
            _uiState.value = UiState.Loading
            updateImage(imageBitmap)
            viewModelScope.launch {
                val result = repo.analyzeImageWithGemini(imageBitmap)
                _uiState.value = UiState.Success(result)
            }
        } catch (t: Throwable) {
            _uiState.value = UiState.Error(t)
        }
    }

    fun updateImage(newImage: Bitmap) {
        image = newImage
    }

}