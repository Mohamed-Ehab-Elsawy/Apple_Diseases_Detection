package com.example.apple_diseases_detection.data.remote.repository

import android.util.Log
import com.example.apple_diseases_detection.data.local.PreferencesHelper
import com.example.apple_diseases_detection.data.models.User
import com.example.apple_diseases_detection.data.models.requests.AuthRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val preferencesHelper: PreferencesHelper,
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    val usersCollection = firestore.collection("Users")

    suspend fun login(authRequest: AuthRequest): Result<FirebaseUser?> {
        try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(
                    authRequest.email,
                    authRequest.password
                ).await()
            val snapshot = usersCollection.document(result.user!!.uid).get().await()
            val user = snapshot.toObject(User::class.java)
            if (user != null) {
                preferencesHelper.putUser(user)
                Log.d("AuthRepository", "User Logged in successfully")
            }
            return Result.success(result.user)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    suspend fun signup(authRequest: AuthRequest): Result<FirebaseUser?> =
        try {
            if (authRequest.user != null) {
                val result = firebaseAuth.createUserWithEmailAndPassword(
                    authRequest.email, authRequest.password
                ).await()

                Log.e("AuthRepository", "User signed up successfully")
                authRequest.user!!.id = result.user!!.uid
                usersCollection.document(authRequest.user!!.id ?: "").set(authRequest.user!!)
                    .await()
                preferencesHelper.putUser(authRequest.user!!)
                Log.e("AuthRepository", "User data saved successfully")
                Result.success(result.user)
            } else
                Result.failure(Throwable("User is null"))
        } catch (t: Throwable) {
            Result.failure(t)
        }

    suspend fun forgetPassword(email: String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
        } catch (t: Throwable) {
            Log.e("FirebaseServicesImpl", "forgetPassword: ${t.localizedMessage}")
        }
    }

}