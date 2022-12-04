package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.LoginResponse
import com.moviles.proyectomoviles.models.RegisterResponse
import com.moviles.proyectomoviles.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LOGINAPI {

    @POST("client/login")
    fun login(@Body user: Usuario): Call<LoginResponse>

    @POST("client/register")
    //create a function with body params
    fun register(@Body user : Usuario): Call<RegisterResponse>
}