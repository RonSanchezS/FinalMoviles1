package com.moviles.proyectomoviles.repository

import android.content.Context
import android.util.Log
import com.moviles.proyectomoviles.api.LOGINAPI
import com.moviles.proyectomoviles.models.LoginResponse
import com.moviles.proyectomoviles.models.RegisterResponse
import com.moviles.proyectomoviles.models.Usuario
import com.moviles.proyectomoviles.models.UsuarioRegister
import retrofit2.Call
import retrofit2.Response


object LoginRepository {
    //generate a login with retrofit2
    fun login(user: Usuario,  context: Context, listener : OnLoginListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val loginApi = retrofit.create(LOGINAPI::class.java)

        //make a post request with loginApi.login and pass the user
        loginApi.login(user).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LoginRepository", "Error en onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LoginRepository", "onResponse: ${loginResponse?.access_token}")
                    listener.onLoginSuccess(loginResponse?.access_token)
                    val token = loginResponse?.access_token.toString()

                    //save the token in shared preferences
                    val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", token)
                    editor.apply()

                } else {
                    listener.onLoginFailure()
                    Log.d("LoginRepository", "Error: ${response.errorBody()}")
                }
            }

        })

    }

    interface OnLoginListener {
        fun onLoginSuccess(accessToken: String?)
        fun onLoginFailure()

    }

    fun register(user: UsuarioRegister, listener : onRegisterListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val loginApi = retrofit.create(LOGINAPI::class.java)
        loginApi.register(user).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("LoginRepository", "Error: ${t.message}")
                listener.onRegisterError(t.message.toString())
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    listener.onRegisterSuccess(response.body()!!)
                    Log.d("LoginRepository", "Registro exitoso")
                } else {
                    Log.d("LoginRepository", "Error: ${response.errorBody()}")
                }
            }
        })
    }

    interface onRegisterListener {
        fun onRegisterSuccess(body: RegisterResponse)
        fun onRegisterError(toString: String)
    }
}