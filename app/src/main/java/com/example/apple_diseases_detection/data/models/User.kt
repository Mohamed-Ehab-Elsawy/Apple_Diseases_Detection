package com.example.apple_diseases_detection.data.models

import com.google.firebase.Timestamp

data class User(
    var id: String? = null,
    val createdAt: Timestamp = Timestamp.now(),
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
)