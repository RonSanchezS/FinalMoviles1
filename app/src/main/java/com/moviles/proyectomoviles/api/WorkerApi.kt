package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Categories
import retrofit2.Call
import retrofit2.http.POST

interface WorkerApi {
    //worker adds new category
    @POST("worker/categories")
    fun addCategory(): Call<List<Categories>>
}