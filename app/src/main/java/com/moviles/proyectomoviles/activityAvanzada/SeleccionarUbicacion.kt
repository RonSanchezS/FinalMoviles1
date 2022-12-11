package com.moviles.proyectomoviles.activityAvanzada

import android.content.ContentProviderClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.moviles.proyectomoviles.R


class SeleccionarUbicacion : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapaIndicaciones: MapView
    private lateinit var map : GoogleMap
    private lateinit var txtInstrucciones : EditText
    private lateinit var btnEnviarUbicacion : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_ubicacion)
        val cotizacionID = intent.getStringExtra("cotizacionID")
        Toast.makeText(this, "Cotizacion ID: $cotizacionID", Toast.LENGTH_SHORT).show()
        setUpListView();
        setUpMap();
        mapaIndicaciones.getMapAsync(this)
        setupListener()
    }

    private fun setupListener() {
        btnEnviarUbicacion.setOnClickListener {
            val instrucciones = txtInstrucciones.text.toString()
            val longitude = map.cameraPosition.target.longitude
            val latitude = map.cameraPosition.target.latitude
            Toast.makeText(this, "Longitud: $longitude, Latitud: $latitude, Instrucciones: $instrucciones", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpMap() {



    }

    private fun setUpListView() {

        mapaIndicaciones = findViewById(R.id.mapaIndicaciones)
        txtInstrucciones = findViewById(R.id.txtInstruccionesDeUbicacion)
        btnEnviarUbicacion = findViewById(R.id.btnEnviarUbicacion)


    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        mapaIndicaciones.onResume()
    }
}