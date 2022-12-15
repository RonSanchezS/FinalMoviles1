package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moviles.proyectomoviles.models.Usuario
import com.moviles.proyectomoviles.repository.LoginRepository

class MainActivity : AppCompatActivity(), LoginRepository.OnLoginListener{
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIngresar: Button

    private lateinit var txtEmailLogin: EditText
    private lateinit var txtPasswordLogin: EditText

    private lateinit var token : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListView()
        setUpListeners()
        //get token from shared preferences
        val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", null)
        Toast.makeText(this, "El toke es $token", Toast.LENGTH_LONG).show()
        if (token != null) {
            val intent = Intent(this, ActivityListaCategorias::class.java)
            startActivity(intent)
        }
        setUpFirebase()


    }
    private fun setUpFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TOKEN","Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result as String

            // Log and toast
            Log.d("TOKEN", token)
            println("TOKEN DE FIREBASE : $token")
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

    }

    private fun setUpListeners() {
        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
        btnIngresar.setOnClickListener {
            //console log the text inside txtEmailLogin and txtPasswordLogin
            val usuarioTemporal = Usuario(
                txtEmailLogin.text.toString(),
                txtPasswordLogin.text.toString(),
                token
            )

            LoginRepository.login(usuarioTemporal, this, this)


        }

    }

    private fun setUpListView() {
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnIngresar = findViewById(R.id.btnIngresar)
        txtEmailLogin = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin)
    }

    override fun onLoginSuccess(accessToken: String?) {
        val intent = Intent(this, ActivityListaCategorias::class.java)
        val sharedpref = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putString("token", accessToken)
        editor.apply()
        startActivity(intent)
    }

    override fun onLoginFailure() {
        Log.e("Login", "Login failed")

        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
    }


}