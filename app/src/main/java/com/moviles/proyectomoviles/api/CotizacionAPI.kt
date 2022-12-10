package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.http.*

interface CotizacionAPI{

    @Headers("content-type: application/json", "Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("client/works")
    fun getWorks(): Call<List<Cotizacion>>

    @POST("work")
    fun crearNuevaCotizacion(@Body trabajo : Trabajo, @Header("Authorization") token : String) : Call<Trabajo>

}