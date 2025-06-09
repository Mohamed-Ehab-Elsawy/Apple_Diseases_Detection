package com.example.apple_diseases_detection.presentation.screens.detection.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.apple_diseases_detection.navigation.MainScreens
import com.example.apple_diseases_detection.presentation.components.ui.theme.black
import com.example.apple_diseases_detection.presentation.screens.detection.DetectionViewModel
import com.example.apple_diseases_detection.presentation.screens.detection.camera.component.CameraView
import com.example.apple_diseases_detection.presentation.screens.detection.camera.component.GalleryButton
import com.example.apple_diseases_detection.presentation.screens.detection.camera.component.ShutterButton
import java.io.File

@Composable
fun CameraScreen(
    navController: NavController,
    vm: DetectionViewModel,
) {
    val context = LocalContext.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    val filePath = getFileFromUri(context, uri).absolutePath
                    val bitmap = BitmapFactory.decodeFile(filePath)
                    vm.analyzeImageWithGemini(bitmap)
                    navController.navigate(MainScreens.Results.route) {
                        popUpTo(MainScreens.Camera.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {

        CameraView(modifier = Modifier.align(Alignment.Center), onCapture = { imageCapture = it })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ShutterButton(modifier = Modifier.align(Alignment.Center), onClick = {
                    onImageTaken(
                        capture = imageCapture, context = context, onSuccess = { photoFile ->
                            val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                            vm.analyzeImageWithGemini(bitmap)
                            navController.navigate(MainScreens.Results.route) {
                                popUpTo(MainScreens.Camera.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                })
            GalleryButton(modifier = Modifier.align(Alignment.CenterStart)) {
                val intent = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                imagePickerLauncher.launch(intent)
            }
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver

    if (uri.scheme == "file") {
        return File(uri.path!!)
    }

    val fileName = getFileName(context, uri)
    val tempFile = File(context.cacheDir, fileName)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    return tempFile
}

fun getFileName(context: Context, uri: Uri): String {
    var name: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    return name ?: "${System.currentTimeMillis()}.jpg"
}

fun onImageTaken(capture: ImageCapture?, context: Context, onSuccess: (File) -> Unit) {
    val executor = ContextCompat.getMainExecutor(context)
    capture?.let { capture ->
        val photoFile = File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        capture.takePicture(
            outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onSuccess.invoke(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            })
    }
}