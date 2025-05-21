package com.example.apple_diseases_detection.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.apple_diseases_detection.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppleDiseasesDetectionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger()
            androidContext(this@AppleDiseasesDetectionApp)
            modules(appModule)
        }
    }
}