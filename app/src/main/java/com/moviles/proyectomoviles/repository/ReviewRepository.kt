package com.moviles.proyectomoviles.repository

import android.util.Log
import com.moviles.proyectomoviles.api.ReviewApi
import com.moviles.proyectomoviles.models.ResponseBody
import com.moviles.proyectomoviles.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReviewRepository {

    fun dejarReview(idTrabajo :Int, review : Review){
        val retrofit = RetrofitRepository.getRetrofit()
        val service = retrofit.create(ReviewApi::class.java)
        service.createReview(idTrabajo, review).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("ReviewRepository", "Review enviada")

                } else {
                    val loginResponse = response.body()
                    println(loginResponse?.message.toString())
                    Log.d("ReviewRepository", "Error al enviar review")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ReviewRepository", "Error al enviar review")
                println("Error al enviar review ${t.message}")

            }

        })
    }
}