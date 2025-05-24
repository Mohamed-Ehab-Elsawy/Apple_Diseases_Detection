package com.example.apple_diseases_detection.presentation.screens.auth.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apple_diseases_detection.data.models.User
import com.example.apple_diseases_detection.data.models.requests.AuthRequest
import com.example.apple_diseases_detection.data.remote.repository.AuthRepository
import com.example.apple_diseases_detection.utils.UiState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<FirebaseUser>>(UiState.Idle)
    val uiState: StateFlow<UiState<FirebaseUser>> = _uiState

    var firstName = mutableStateOf("")
        private set
    var lastName = mutableStateOf("")
        private set
    var email = mutableStateOf("")
        private set
    var phoneNumber = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set
    var confirmPassword = mutableStateOf("")
        private set

    var firstNameError = mutableStateOf<String?>(null)
        private set
    var lastNameError = mutableStateOf<String?>(null)
        private set
    var emailError = mutableStateOf<String?>(null)
        private set
    var phoneNumberError = mutableStateOf<String?>(null)
        private set
    var passwordError = mutableStateOf<String?>(null)
        private set
    var confirmPasswordError = mutableStateOf<String?>(null)
        private set


    fun signup() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val user = User(
                    firstName = firstName.value,
                    lastName = lastName.value,
                    email = email.value,
                    phoneNumber = phoneNumber.value,
                )
                val request = AuthRequest(email.value, password.value, user)

                val result = repo.signup(request)
                result.onSuccess { user ->
                    if (user != null) _uiState.value = UiState.Success(user)
                }
                result.onFailure { t ->
                    _uiState.value = UiState.Error(t)
                }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (firstName.value.isEmpty()) {
            firstNameError.value = "Enter your name"
            isValid = false
        } else firstNameError.value = null

        if (lastName.value.isEmpty()) {
            lastNameError.value = "Enter your last name"
            isValid = false
        } else lastNameError.value = null

        if (email.value.isEmpty()) {
            emailError.value = "Enter your email address"
            isValid = false
        } else emailError.value = null

        if (phoneNumber.value.isEmpty()) {
            phoneNumberError.value = "Enter your phone number"
            isValid = false
        } else phoneNumberError.value = null

        if (password.value.isEmpty()) {
            passwordError.value = "Enter your password"
            isValid = false
        } else if (password.value.length < 8) {
            passwordError.value = "Password must be 8 or more characters"
            isValid = false
        } else passwordError.value = null

        if (confirmPassword.value.isEmpty()) {
            confirmPasswordError.value = "Confirm your password"
            isValid = false
        } else if (confirmPassword.value != password.value) {
            confirmPasswordError.value = "Passwords do not match"
            isValid = false
        } else confirmPasswordError.value = null

        Log.e("PatientSignupViewModel", "data validation: $isValid")
        return isValid
    }

    fun onFirstNameChange(newName: String) {
        firstName.value = newName
    }

    fun onLastNameChange(newName: String) {
        lastName.value = newName
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        phoneNumber.value = newPhoneNumber
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

}