package com.moviles.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.adapters.CotizacionAdapter
import com.moviles.proyectomoviles.models.Trabajo
import com.moviles.proyectomoviles.repository.CotizacionRepository

class CotizacionChat : AppCompatActivity(), CotizacionRepository.onPostCotizacionListener {

    private lateinit var recyclerCharla : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_chat)
       setUpContent()
    }

    private fun setUpContent() {
        recyclerCharla = findViewById(R.id.recyclerCharla)
        val workerId = intent.extras?.get("workerID").toString()
        val token = getSharedPreferences("token", MODE_PRIVATE).getString("token", "")
        val categoryID = intent.extras?.get("cotizacionID").toString()
        val trabajo = Trabajo(workerId, categoryID)
        CotizacionRepository.postCotizacion(trabajo, "Bearer $token", this)

    }

    override fun onFailure(t: Throwable) {
        println("Error al enviar la cotizacion ${t.message}")
    }

    override fun onSuccess(body: Trabajo?) {
        println("Cotizacion enviada")

    }
}