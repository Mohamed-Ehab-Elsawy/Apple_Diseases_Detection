package com.example.apple_diseases_detection.di

import androidx.appcompat.app.AppCompatDelegate
import org.koin.dsl.module

val appModule = module {
    single { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
}