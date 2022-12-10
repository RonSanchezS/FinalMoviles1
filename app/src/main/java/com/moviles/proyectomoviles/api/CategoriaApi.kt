package com.moviles.proyectomoviles.api

import com.moviles.proyectomoviles.models.Categories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoriaApi {
    @Headers("content-type: application/json", "Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("categories")
    fun getCategoriass(): Call<List<Categories>>

    @Headers("content-type: application/json", "Authorization: Bearer 89|MI8jytv2cqhMLExO9tL3VliwDXgsgrJYp7SLTl5l")
    @GET("categories/{id}/workers")
    fun getCategoriasWithId(@Path ("id") id : Int): Call<Categories>

    //post categories manually
    @POST("categories")
    fun postCategorias(string: String): Call<List<Categories>>


}