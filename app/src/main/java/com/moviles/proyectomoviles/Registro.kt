package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Registro : AppCompatActivity() {
    private lateinit var btnRegistrarCuenta : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        setUpListView();
        setUpListeners();
    }

    private fun setUpListeners() {
        btnRegistrarCuenta.setOnClickListener {
            finish()
        }
    }

    private fun setUpListView() {
        btnRegistrarCuenta = findViewById(R.id.btnRegistrarCuenta)
    }
}