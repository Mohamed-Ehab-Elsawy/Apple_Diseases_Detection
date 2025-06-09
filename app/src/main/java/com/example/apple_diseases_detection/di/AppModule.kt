package com.example.apple_diseases_detection.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.apple_diseases_detection.data.local.PreferencesHelper
import com.example.apple_diseases_detection.data.remote.repository.AuthRepository
import com.example.apple_diseases_detection.data.remote.repository.DetectionRepository
import com.example.apple_diseases_detection.presentation.screens.auth.forget_password.ForgetPasswordViewModel
import com.example.apple_diseases_detection.presentation.screens.auth.login.LoginViewModel
import com.example.apple_diseases_detection.presentation.screens.auth.register.SignupViewModel
import com.example.apple_diseases_detection.presentation.screens.detection.DetectionViewModel
import com.example.apple_diseases_detection.presentation.screens.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }


    single<SharedPreferences> {
        val context: Context = androidContext()
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PreferencesHelper.SHARED_PREFERENCE_KEY,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single { PreferencesHelper(get()) }
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { AuthRepository(get(), get(), get()) }
    single { DetectionRepository() }

    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ForgetPasswordViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DetectionViewModel(get()) }

}