package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.adapters.CategoryAdapter
import com.moviles.proyectomoviles.models.Categories
import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.repository.CategoryRepository
import com.moviles.proyectomoviles.repository.CotizacionRepository

class ActivityListaCategorias : AppCompatActivity(), CategoryRepository.OnGetCategoriesListener,
    CategoryAdapter.onCategoryClickListener, CotizacionRepository.CotizacionListener {
    private lateinit var lstCategorias: RecyclerView
    private lateinit var btnBusqueda: ImageButton
    private lateinit var btnVerCotizaciones: Button
    private lateinit var inputPorBuscar: TextView

    private lateinit var listaCategorias: List<Categories>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        setUpListView()
        setUpListeners()
        callCategoriesApi()
        getIDFromApi()
    }

    private fun getIDFromApi() {


    }

    private fun setUpListeners() {
        btnVerCotizaciones.setOnClickListener {
            val intent = Intent(this, ActivityCotizar::class.java)
            startActivity(intent)
        }
        btnBusqueda.setOnClickListener {
            val textoCategoria =  inputPorBuscar.text.toString()
            //filter categorias by textoCategoria
            val categoriasFiltradas = listaCategorias.filter { it.name.contains(textoCategoria) }
            val adapter = CategoryAdapter(categoriasFiltradas as ArrayList<Categories>, this)
            lstCategorias.layoutManager = LinearLayoutManager(this)
            lstCategorias.adapter = adapter

        }

    }

    private fun callCategoriesApi() {
        CategoryRepository.getCategorias(this)
    }

    private fun setUpListView() {
        lstCategorias = findViewById(R.id.lstCategories)
        btnBusqueda = findViewById(R.id.btnBuscar)
        btnVerCotizaciones = findViewById(R.id.btnVerCotizaciones)
        inputPorBuscar = findViewById(R.id.inputPorBuscar)
    }

    override fun onGetCategories(categories: List<Categories>?) {
        listaCategorias = categories!!
        val adapter = CategoryAdapter(categories as ArrayList<Categories>, this)
        lstCategorias.layoutManager =
            LinearLayoutManager(this@ActivityListaCategorias)
        lstCategorias.adapter = adapter
    }


    override fun onGetCategoriesError(t: Throwable) {
        //print the error
        Toast.makeText(this@ActivityListaCategorias, t.message, Toast.LENGTH_SHORT).show()

    }

    override fun onGetCategoriesByID(category: Categories?) {
        //wont be used
    }

    override fun onCategoryClick(category: Categories) {

        val intent = Intent(this, TrabajadoresDeCategoria::class.java)
        intent.putExtra("categoryID", category.id)
        intent.putExtra("categoryName", category.name)
        startActivity(intent)
    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(this@ActivityListaCategorias, "No se pudo sacar datos", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSuccess(cotizaciones: List<Cotizacion>?) {
//        val intent = Intent(this, ActivityListaCotizaciones::class.java)
//        intent.putExtra("cotizaciones", cotizaciones as ArrayList<Cotizacion>)
//        startActivity(intent)
    }
}