package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.ResponseBody
import com.moviles.proyectomoviles.models.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApi {
    @Headers("content-type: application/json")
    @POST("work/{id}/review")
    fun createReview(@Path("id") id: Int, @Body review: Review, @Header("Authorization") token : String): Call<ResponseBody>
}