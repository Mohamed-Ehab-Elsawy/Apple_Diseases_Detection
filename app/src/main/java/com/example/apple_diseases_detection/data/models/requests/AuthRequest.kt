package com.example.apple_diseases_detection.data.models.requests

import com.example.apple_diseases_detection.data.models.User

data class AuthRequest(
    val email: String,
    val password: String,
    var user: User?= null
)