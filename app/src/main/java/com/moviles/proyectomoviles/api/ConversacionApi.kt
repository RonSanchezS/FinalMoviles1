package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Mensaje
import com.moviles.proyectomoviles.models.Ubicacion
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ConversacionApi {

//   @HTTP(method = "GET", path = "work/{id}/messages", hasBody = true)
   @GET("work/{id}/messages")
   fun getMensajesDeConversacion(@Header("Authorization")token : String, @Path("id") id : String) : Call<List<CharlaItem>>

   //add a message
   @POST("work/{id}/chat")
   fun enviarMensaje(@Header("Authorization")token : String, @Path("id") id : String, @Body mensaje : Mensaje) : Call<CharlaItem>

   @POST("work/{id}/chat")
   fun enviarUbicacion(@Header("Authorization")token : String, @Path("id") id : String, @Body ubicacion : Ubicacion) : Call<CharlaItem>

   @Multipart
   @POST("work/{id}/chat")
   fun uploadProfilePicture(@Path("id") id : Int, @Header("Authorization") token : String, @Part filePart : MultipartBody.Part): Call<Mensaje>

}