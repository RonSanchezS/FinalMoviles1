package com.moviles.proyectomoviles.repository

import com.moviles.proyectomoviles.api.CategoriaApi
import com.moviles.proyectomoviles.models.Categories
import retrofit2.Call
import retrofit2.Response

object CategoryRepository {
    fun getCategorias(listener: OnGetCategoriesListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val categoriaApi = retrofit.create(CategoriaApi::class.java)
        categoriaApi.getCategoriass().enqueue(object : retrofit2.Callback<List<Categories>> {
            override fun onResponse(
                call: Call<List<Categories>>,
                response: Response<List<Categories>>
            ) {
                if (response.isSuccessful) {
                    listener.onGetCategories(response.body())
                }
            }

            override fun onFailure(call: Call<List<Categories>>, t: Throwable) {
                println("Error")
                println("Error ${t.message}")
            }

        })
    }



    fun getCategoriasWithIds(id: Int, listener: OnGetCategoriesListener) {
        val retrofit = RetrofitRepository.getRetrofit()
        val categoriaApi = retrofit.create(CategoriaApi::class.java)
        categoriaApi.getCategoriasWithId(id).enqueue(object : retrofit2.Callback<Categories> {
            override fun onResponse(
                call: Call<Categories>,
                response: Response<Categories>
            ) {
                if (response.isSuccessful) {
                    listener.onGetCategoriesByID(response.body())
                    println("hola mundosadoasdsad")
                    println(response.body())
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                println("Error")
                println("Error ${t.message}")

            }

        })
    }
    interface OnGetCategoriesListener {
        fun onGetCategories(categories: List<Categories>?)
        fun onGetCategoriesError(t: Throwable)
        fun onGetCategoriesByID(category: Categories?)
    }
    }




