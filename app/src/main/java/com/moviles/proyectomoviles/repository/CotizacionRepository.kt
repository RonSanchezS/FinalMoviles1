package com.moviles.proyectomoviles.repository

import com.moviles.proyectomoviles.api.CategoriaApi
import com.moviles.proyectomoviles.api.CotizacionAPI
import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.models.Trabajo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CotizacionRepository {
    fun getCotizaciones(listener : CotizacionListener, token : String){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(CotizacionAPI::class.java)
        service.getWorks("Bearer $token").enqueue(object : Callback<List<Cotizacion>> {
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

    fun postCotizacion(trabajo : Trabajo, token : String, listener : onPostCotizacionListener){
        val retrofit = RetrofitRepository.getRetrofit()
        val cotizacionApi = retrofit.create(CotizacionAPI::class.java)
        cotizacionApi.crearNuevaCotizacion(trabajo, token).enqueue(object : Callback<Trabajo>{
            override fun onResponse(call: Call<Trabajo>, response: Response<Trabajo>) {
                if (response.isSuccessful){
                    listener.onSuccess(response.body())

                }else{
                    listener.onFailure(Throwable("Error al crear la cotizacion"))
                }
            }

            override fun onFailure(call: Call<Trabajo>, t: Throwable) {
                listener.onFailure(t)
            }

        })
    }

    interface onPostCotizacionListener {
        fun onFailure(t: Throwable)
        fun onSuccess(body: Trabajo?)

    }

    interface CotizacionListener {
        fun onFailure(t: Throwable)
        fun onSuccess(cotizaciones: List<Cotizacion>?)
    }
}