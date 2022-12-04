package com.moviles.proyectomoviles.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    fun getRetrofit(): Retrofit {
        //generate builder with header
        return Retrofit.Builder()
            .baseUrl("http://works.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}