package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.moviles.proyectomoviles.models.Usuario
import com.moviles.proyectomoviles.repository.LoginRepository

class MainActivity : AppCompatActivity() {
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIngresar: Button

    private lateinit var txtEmailLogin: EditText
    private lateinit var txtPasswordLogin: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListView()
        setUpListeners()
        //get token from shared preferences
        val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", null)
        if (token != null) {
            val intent = Intent(this, ActivityListaCategorias::class.java)
            startActivity(intent)
        }

    }

    private fun setUpListeners() {
        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
        btnIngresar.setOnClickListener {
            //console log the text inside txtEmailLogin and txtPasswordLogin
            var usuarioTemporal = Usuario(
                txtEmailLogin.text.toString(),
                txtPasswordLogin.text.toString(),
                "test-notification-id"
            )

            LoginRepository.login(usuarioTemporal, this)
            val intent = Intent(this, ActivityListaCategorias::class.java)
            startActivity(intent)

        }

    }

    private fun setUpListView() {
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnIngresar = findViewById(R.id.btnIngresar)
        txtEmailLogin = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin)
    }
}