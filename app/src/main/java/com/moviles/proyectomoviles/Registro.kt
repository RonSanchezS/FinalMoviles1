package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.moviles.proyectomoviles.models.RegisterResponse
import com.moviles.proyectomoviles.models.Usuario
import com.moviles.proyectomoviles.models.UsuarioRegister
import com.moviles.proyectomoviles.repository.LoginRepository

class Registro : AppCompatActivity(), LoginRepository.onRegisterListener {
    private lateinit var btnRegistrarCuenta : Button

    private lateinit var txtNombre : EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtContrasena : EditText
    private lateinit var txtPhone : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        setUpListView();
        setUpListeners();
    }

    private fun setUpListeners() {
        btnRegistrarCuenta.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val contrasena = txtContrasena.text.toString()
            val phone = txtPhone.text.toString()
            val usuario = UsuarioRegister(nombre, correo, contrasena, phone)
            LoginRepository.register(usuario,this);
            finish()
        }
    }

    private fun setUpListView() {
        btnRegistrarCuenta = findViewById(R.id.btnRegistrarCuenta)
        txtNombre = findViewById(R.id.txtName)
        txtCorreo = findViewById(R.id.txtMail)
        txtContrasena = findViewById(R.id.txtPass)
        txtPhone = findViewById(R.id.txtPhone)
    }

    override fun onRegisterSuccess(body: RegisterResponse) {
        finish()
    }

    override fun onRegisterError(toString: String) {
        //restart all the fields and show error
        txtNombre.setText("")
        txtCorreo.setText("")
        txtContrasena.setText("")
        txtPhone.setText("")
        Toast.makeText(this, "Ha ocurrido un error, intentalo de nuevo", Toast.LENGTH_LONG).show()
    }
}