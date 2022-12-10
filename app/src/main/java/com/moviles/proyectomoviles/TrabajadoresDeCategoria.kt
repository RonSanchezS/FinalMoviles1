package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.adapters.WorkerInsideCategoryAdapter
import com.moviles.proyectomoviles.models.Categories
import com.moviles.proyectomoviles.models.Worker
import com.moviles.proyectomoviles.models.Workers
import com.moviles.proyectomoviles.repository.CategoryRepository

class TrabajadoresDeCategoria : AppCompatActivity(), CategoryRepository.OnGetCategoriesListener,
    WorkerInsideCategoryAdapter.onWorkerListener {
    private lateinit var txtNombreCategiria : TextView
    private lateinit var lstCategoryWorkers : RecyclerView
    private  var categoryID : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajadores_de_categoria)

        setUpListView();

        //get categoryID from extras
//        Toast.makeText(this, intent.getStringExtra("categoryID"), Toast.LENGTH_SHORT).show()
         categoryID = intent.extras?.getInt("categoryID")!!
        Toast.makeText(this, categoryID.toString(), Toast.LENGTH_SHORT).show()
        if (categoryID != null) {
            callCategoriesWithWorkers(categoryID)
        };
    }

    private fun setUpListView() {

        txtNombreCategiria = findViewById(R.id.txtNombreCategoria)
        txtNombreCategiria.text = "Trabajadores de la categoria ${intent.extras?.getString("categoryName")}"
        lstCategoryWorkers = findViewById(R.id.lstCategoryWorkers)


    }

    private fun callCategoriesWithWorkers(int: Int) {
        CategoryRepository.getCategoriasWithIds(int, this)
    }



    override fun onGetCategories(categories: List<Categories>?) {
        //wont be used
    }

    override fun onGetCategoriesError(t: Throwable) {
//wont be used
    }

    override fun onGetCategoriesByID(category: Categories?) {
            val adapter = category?.workers?.let { WorkerInsideCategoryAdapter(it, this) }
            lstCategoryWorkers.layoutManager = LinearLayoutManager(this)
            lstCategoryWorkers.adapter = adapter



    }


    override fun onWorkerClick(worker: Workers) {
        val intent = Intent(this, ActivityPerfilTrabajador::class.java)

        intent.putExtra("workerID", worker.worker?.id)
        intent.putExtra("workerName", worker.worker?.user?.name)
        intent.putExtra("workerPhone", worker.worker?.phone)
        intent.putExtra("categoryID", categoryID)
        intent.putExtra("categoryName", intent.extras?.getString("categoryName"))
        intent.putExtra("workerImgLink", worker.worker?.profilePicture)

        startActivity(intent)
    }


}