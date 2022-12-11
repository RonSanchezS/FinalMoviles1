package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LOGINAPI {

    @POST("client/login")
    fun login(@Body user: Usuario): Call<LoginResponse>

    @POST("client/register")
    //create a function with body params
    fun register(@Body user : UsuarioRegister): Call<RegisterResponse>

    @GET("me")
    fun getDatosUsuario(@Header("Authorization") token : String) : Call<Trabajador>
}