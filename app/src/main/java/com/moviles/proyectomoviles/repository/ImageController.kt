package com.moviles.proyectomoviles.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import java.io.File

object ImageController {
    fun selectPhotoFromGallery( resultLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    fun saveImage(context: Context, id: Int, uri: Uri) {
        val image = File(context.filesDir, id.toString())
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
        image.writeBytes(bytes!!)
    }

    fun getImage(context: Context): Uri {
        val image = File(context.filesDir, "1")
        return Uri.fromFile(image)
    }
}