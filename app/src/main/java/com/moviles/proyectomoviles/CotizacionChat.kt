package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.adapters.CotizacionAdapter
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Trabajo
import com.moviles.proyectomoviles.repository.ConversacionRepository
import com.moviles.proyectomoviles.repository.CotizacionRepository

class CotizacionChat : AppCompatActivity(), ConversacionRepository.onConversacionGetListener {

    private lateinit var recyclerCharla: RecyclerView

    private lateinit var token: String
    private lateinit var cotizacionID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_chat)
        setUpContent()

    }

    private fun setUpContent() {
        recyclerCharla = findViewById(R.id.recyclerCharla)
        token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "").toString()
        cotizacionID = intent.extras?.get("cotizacionID").toString()



        ConversacionRepository.getMensajesDeConversacion("Bearer $token", cotizacionID.toString(), this)


    }


    override fun onConversacionGetError(t: Throwable) {
    println(t.message)
    }

    override fun onConversacionGetSuccess(body: List<CharlaItem>) {

    }


}