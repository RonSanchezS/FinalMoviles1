package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ConversacionApi {

   @GET("work/{id}/messages")
   fun getMensajesDeConversacion(@Header("Authorization")token : String, @Body id : String) : Call<List<Trabajo>>

   }