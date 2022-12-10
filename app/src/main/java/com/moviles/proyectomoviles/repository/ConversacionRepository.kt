package com.moviles.proyectomoviles.repository

import com.moviles.proyectomoviles.api.ConversacionApi
import com.moviles.proyectomoviles.models.CharlaItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ConversacionRepository {

    fun getMensajesDeConversacion(token : String, id : String, listener : onConversacionGetListener ) {
        val retrofit = RetrofitRepository.getRetrofit()
        val conversacionApi = retrofit.create(ConversacionApi::class.java)
        conversacionApi.getMensajesDeConversacion(token, id).enqueue(object : Callback<List<CharlaItem>> {
            override fun onFailure(call: Call<List<CharlaItem>>, t: Throwable) {
                listener.onConversacionGetError(t)
            }

            override fun onResponse(call: Call<List<CharlaItem>>, response: Response<List<CharlaItem>>) {
                if (response.isSuccessful) {
                    listener.onConversacionGetSuccess(response.body()!!)
                } else {
                    listener.onConversacionGetError(Throwable("Error al obtener conversacion ${response.code()}"))
                }
            }
        })
    }

    interface onConversacionGetListener {
        fun onConversacionGetError(t: Throwable)
        fun onConversacionGetSuccess(body: List<CharlaItem>)

    }
}