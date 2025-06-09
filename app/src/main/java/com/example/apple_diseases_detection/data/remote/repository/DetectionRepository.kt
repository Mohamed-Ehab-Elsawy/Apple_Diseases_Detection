package com.example.apple_diseases_detection.data.remote.repository

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content

class DetectionRepository {
    val model =
        Firebase.ai(backend = GenerativeBackend.vertexAI()).generativeModel("gemini-2.0-flash")


    suspend fun analyzeImageWithGemini(imageBitmap: Bitmap): String {
        try {
            val prompt =
                "In the image, there is an apple tree leaf. If the leaf is affected by any disease, please identify the disease name in the first line only" +
                        "Then, skip two lines, and provide one effective treatment method" +
                        "Please make sure the entire response uses normal tone formatting, so it can be displayed directly in a mobile application using Android Jetpack Compose.\n"
            var resultText = ""
            val multimodalContent = content {
                image(imageBitmap)
                text(prompt)
            }

            val response = model.generateContent(multimodalContent)
            resultText = response.text ?: ""

            Log.e("TAG", "Gemini Analysis Result: $resultText")
            return resultText
        } catch (e: Exception) {
            Log.e("TAG", "Error analyzing image: ${e.localizedMessage}")
            return ""
        }
    }
}