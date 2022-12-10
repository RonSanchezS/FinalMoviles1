package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ConversacionApi {

//   @HTTP(method = "GET", path = "work/{id}/messages", hasBody = true)
   @GET("work/{id}/messages")
   fun getMensajesDeConversacion(@Header("Authorization")token : String, @Path("id") id : String) : Call<List<CharlaItem>>

   }