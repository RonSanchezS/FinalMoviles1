package com.moviles.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class FullScreenImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        val imagen = intent.extras?.get("image") as String
        val image = findViewById<ImageView>(R.id.imageView2)
        Glide.with(this).load(imagen).into(image)
    }
}