package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.adapters.CotizacionAdapter
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Cotizacion
import com.moviles.proyectomoviles.repository.ConversacionRepository
import com.moviles.proyectomoviles.repository.CotizacionRepository

class ActivityCotizar : AppCompatActivity(), CotizacionRepository.CotizacionListener,
    CotizacionAdapter.onCotizacionClickListener, ConversacionRepository.onConversacionGetListener {
    private lateinit var recyclerCotizaciones: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizar)
        recyclerCotizaciones = findViewById(R.id.recyclerViewCotizaciones)
        val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "")
        CotizacionRepository.getCotizaciones(this, token.toString())
    }

    override fun onFailure(t: Throwable) {
        println("Error: ${t.message}")
    }

    override fun onSuccess(cotizaciones: List<Cotizacion>?) {
        val adapter = CotizacionAdapter(cotizaciones as ArrayList<Cotizacion>, this)
        recyclerCotizaciones.layoutManager = LinearLayoutManager(this@ActivityCotizar)
        recyclerCotizaciones.adapter = adapter

    }

    override fun onCotizacionClick(cotizacion: Cotizacion) {
        println(cotizacion)
        val intent = Intent(this, ActivityPuntuar::class.java)
        intent.putExtra("idCotizacion", cotizacion.id)
        intent.putExtra("idUsuario", cotizacion.clientId)
        intent.putExtra("idTrabajador", cotizacion.workerId)
        intent.putExtra("idTrabajo", cotizacion.id)

        intent.putExtra("estado", cotizacion.status)
        if (cotizacion.review != null) {
            intent.putExtra("review", cotizacion.review)
        } else {
            intent.putExtra("review", 0.0f)

        }

        startActivity(intent)
    }

    override fun onCharlaOpen(cotizacion: Cotizacion) {
        val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "")
        val intent = Intent(this, CotizacionChat::class.java)
        intent.putExtra("cotizacionID", cotizacion.id)
        Toast.makeText(this, "Cotizacion ID: ${cotizacion.id}", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }


    override fun onConversacionGetError(t: Throwable) {
        println(t.message)
    }

    override fun onConversacionGetSuccess(body: List<CharlaItem>) {
        TODO("Not yet implemented")
    }
}