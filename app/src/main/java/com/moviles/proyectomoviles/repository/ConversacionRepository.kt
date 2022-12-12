package com.moviles.proyectomoviles.repository

import android.net.Uri
import com.moviles.proyectomoviles.CotizacionChat
import com.moviles.proyectomoviles.api.ConversacionApi
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Mensaje
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

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

    fun uploadProfilePicture(idCotizacion : String, token : String, fileUri: Uri,listener : onProfilePictureUploadListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(ConversacionApi::class.java)
        val file = fileUri.path?.let { File(it) }
        if (file == null) {
            listener.onProfilePictureFailed(java.lang.Exception("No se pudo obtener el archivo"))
            return
        }

        val filePart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            RequestBody.create(MediaType.parse("image/*"), file)
        )
        service.uploadProfilePicture(idCotizacion.toInt(), "Bearer $token", filePart).enqueue(
            object : Callback<Mensaje> {
                override fun onFailure(call: Call<Mensaje>, t: Throwable) {
                    listener.onProfilePictureFailed(t as Exception)
                }

                override fun onResponse(call: Call<Mensaje>, response: Response<Mensaje>) {
                    if (response.isSuccessful) {
                        listener.onProfilePictureSuccess(response.body()!!)
                    } else {
                        listener.onProfilePictureFailed(java.lang.Exception("Error al subir la imagen"))
                    }
                }
            }
        )



    }

    interface onProfilePictureUploadListener {
        fun onProfilePictureFailed(exception: Exception)
        fun onProfilePictureSuccess(body: Mensaje)

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


