package com.moviles.proyectomoviles.repository

import com.moviles.proyectomoviles.api.CategoriaApi
import com.moviles.proyectomoviles.api.CotizacionAPI
import com.moviles.proyectomoviles.models.Cotizacion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CotizacionRepository {
    fun getCotizaciones(listener : CotizacionListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(CotizacionAPI::class.java)
        service.getWorks().enqueue(object : Callback<List<Cotizacion>> {
            override fun onFailure(call: Call<List<Cotizacion>>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<List<Cotizacion>>, response: Response<List<Cotizacion>>) {
                if (response.isSuccessful){
                    println(response.body())
                    val cotizaciones = response.body()
                    listener.onSuccess(cotizaciones)
                }else{
                    listener.onFailure(Throwable("Error al obtener las cotizaciones"))
                }
            }
        })
    }

    interface CotizacionListener {
        fun onFailure(t: Throwable)
        fun onSuccess(cotizaciones: List<Cotizacion>?)
    }
}