package com.moviles.proyectomoviles.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.moviles.proyectomoviles.api.LOGINAPI
import com.moviles.proyectomoviles.models.LoginResponse
import com.moviles.proyectomoviles.models.RegisterResponse
import com.moviles.proyectomoviles.models.Usuario
import retrofit2.Call
import retrofit2.Response


object LoginRepository {
    //generate a login with retrofit2
    fun login(user: Usuario,  context: Context) {
        val retrofit = RetrofitRepository.getRetrofit()
        val loginApi = retrofit.create(LOGINAPI::class.java)
        var token = ""
        //make a post request with loginApi.login and pass the user
        loginApi.login(user).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LoginRepository", "Error en onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LoginRepository", "onResponse: ${loginResponse?.access_token}")
                    token = loginResponse?.access_token.toString()

                    //save the token in shared preferences
                    val sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", token)
                    editor.apply()

                    val gson = Gson()
                    val favData = gson.toJson(response.body())
                } else {
                    Log.d("LoginRepository", "Error: ${response.errorBody()}")
                }
            }

        })

    }

    fun register(user: Usuario) {
        val retrofit = RetrofitRepository.getRetrofit()
        val loginApi = retrofit.create(LOGINAPI::class.java)
        loginApi.register(user).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("LoginRepository", "Error: ${t.message}")
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("LoginRepository", "Registro exitoso")
                } else {
                    Log.d("LoginRepository", "Error: ${response.errorBody()}")
                }
            }
        })
    }
}