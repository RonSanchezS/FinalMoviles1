package com.moviles.proyectomoviles.repository

import com.moviles.proyectomoviles.CotizacionChat
import com.moviles.proyectomoviles.api.ConversacionApi
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Mensaje
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ConversacionRepository {

    fun getMensajesDeConversacion(token: String, id: String, listener: onConversacionGetListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val conversacionApi = retrofit.create(ConversacionApi::class.java)
        conversacionApi.getMensajesDeConversacion(token, id)
            .enqueue(object : Callback<List<CharlaItem>> {
                override fun onFailure(call: Call<List<CharlaItem>>, t: Throwable) {
                    listener.onConversacionGetError(t)
                }

                override fun onResponse(
                    call: Call<List<CharlaItem>>,
                    response: Response<List<CharlaItem>>
                ) {
                    if (response.isSuccessful) {
                        listener.onConversacionGetSuccess(response.body()!!)
                    } else {
                        listener.onConversacionGetError(Throwable("Error al obtener conversacion ${response.code()}"))
                    }
                }
            })
    }

    fun enviarMensaje(
        token: String,
        mensaje: Mensaje,
        id: String,
        listener: onMensajeSendListener2
    ) {
        val retrofit = RetrofitRepository.getRetrofit()
        val conversacionApi = retrofit.create(ConversacionApi::class.java)
        conversacionApi.enviarMensaje("Bearer $token", id, mensaje)
            .enqueue(object : Callback<CharlaItem> {
                override fun onResponse(call: Call<CharlaItem>, response: Response<CharlaItem>) {

                    if (response.isSuccessful) {
                        listener.onMensajeSendSuccess(response.body()!!)
                    } else {
                        listener.onMensajeSendError(Throwable("Error al enviar mensaje ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<CharlaItem>, t: Throwable) {
                    listener.onMensajeSendError(t)
                }

            })




//        conversacionApi.enviarMensaje("Bearer $token", id, mensaje)
//            .enqueue(object : Callback<CharlaItem> {
//                override fun onResponse(
//                    call: Call<CharlaItem>,
//                    response: Response<CharlaItem>
//                ) {
//                    if (response.isSuccessful) {
//                        listener.onMensajeSendSuccess(response.body()!!)
//                    } else {
//                        listener.onMensajeSendError(Throwable("Error al enviar mensaje ${response.code()}"))
//                    }
//                }
//
//                override fun onFailure(call: Call<CharlaItem>, t: Throwable) {
//                    listener.onMensajeSendError(t)
//                }
//
//
//            })
    }

    interface onMensajeSendListener2 {
        fun onMensajeSendSuccess(body: CharlaItem)
        fun onMensajeSendError(throwable: Throwable)

    }

    interface onConversacionGetListener {
        fun onConversacionGetSuccess(mensajes: List<CharlaItem>)
        fun onConversacionGetError(t: Throwable)
    }
//
//    interface onMensajeSendListener {
//         fun onMensajeSendSuccess(body: CharlaItem)
//         fun onMensajeSendError(t: Throwable)
//
//    }

}


