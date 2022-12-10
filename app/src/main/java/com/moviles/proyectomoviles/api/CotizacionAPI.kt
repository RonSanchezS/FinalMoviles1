package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.http.*

interface CotizacionAPI{

    @Headers("content-type: application/json")
    @GET("client/works")
    fun getWorks(@Header("Authorization") token : String): Call<List<Cotizacion>>

    @POST("work")
    fun crearNuevaCotizacion(@Body trabajo : Trabajo, @Header("Authorization") token : String) : Call<Trabajo>

}