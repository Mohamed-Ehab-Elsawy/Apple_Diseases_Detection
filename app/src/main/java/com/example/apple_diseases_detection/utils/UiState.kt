package com.example.apple_diseases_detection.utils

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val t: Throwable) : UiState<Nothing>()
}