package com.example.apple_diseases_detection.presentation.screens.profile

import androidx.lifecycle.ViewModel
import com.example.apple_diseases_detection.data.remote.repository.AuthRepository

class ProfileViewModel(
    private val repo: AuthRepository
) : ViewModel() {
    fun logout() {
        repo.logout()
    }
}