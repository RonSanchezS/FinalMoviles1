package com.moviles.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CotizacionChat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_chat)
        val workerId = intent.extras?.get("workerID").toString()
        val token = getSharedPreferences("token", MODE_PRIVATE).getString("token", "")
        val categoryID = intent.extras?.get("cotizacionID").toString()
        println("Worker id : $workerId")
        println("token $token")
        println("Category ID $categoryID")
    }
}