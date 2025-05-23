package com.example.apple_diseases_detection.data.local

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.apple_diseases_detection.data.models.User
import com.google.gson.Gson

class PreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val TAG = "Preferences Helper"
        const val SHARED_PREFERENCE_KEY = "com.cs.apple_diseases_detection"
        const val USER = "user"
    }

    private val gson = Gson()

    fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun fetchString(key: String, default: String = ""): String =
        sharedPreferences.getString(key, default) ?: ""

    fun clearString(key: String) {
        sharedPreferences.edit { putString(key, "") }
    }

    fun putUser(user: User) {
        val patientDataJson = gson.toJson(user)
        putString(USER, patientDataJson)
        Log.e(TAG, "Patient saved, id: ${user.id}, name: ${user.name}")
    }

    fun fetchUser(): User {
        val json = fetchString(USER)
        return if (json.isNotEmpty()) {
            val patient = gson.fromJson(json, User::class.java)
            Log.e(TAG, "Patient fetched, id:${patient.id}, name: ${patient.name}")
            patient
        } else User()
    }

    fun clearData() {
        sharedPreferences.edit {
            clearString(USER)
            Log.e(TAG, "Data cleared")
        }
    }

}