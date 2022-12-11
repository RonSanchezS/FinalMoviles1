package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.models.Instruccion
import com.moviles.proyectomoviles.models.Mensaje
import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.http.*

interface CotizacionAPI{

    @Headers("content-type: application/json")
    @GET("client/works")
    fun getWorks(@Header("Authorization") token : String): Call<List<Cotizacion>>

    @POST("work")
    fun crearNuevaCotizacion(@Body trabajo : Trabajo, @Header("Authorization") token : String) : Call<Trabajo>

    @POST("work/{id}/accept")
    fun aceptarCotizacion(@Path("id") id : Int, @Header("Authorization") token : String, @Body instruccion: Instruccion) : Call<Mensaje>

    @POST("work/{id}/reject")
    fun rechazarCotizacion(@Path("id") id : Int, @Header("Authorization") token : String) : Call<Mensaje>
}