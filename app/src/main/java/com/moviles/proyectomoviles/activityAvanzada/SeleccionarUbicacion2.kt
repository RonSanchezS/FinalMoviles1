package com.moviles.proyectomoviles.activityAvanzada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.databinding.ActivitySeleccionarUbicacion2Binding
import com.moviles.proyectomoviles.models.Instruccion
import com.moviles.proyectomoviles.models.Mensaje
import com.moviles.proyectomoviles.repository.CotizacionRepository

class SeleccionarUbicacion2 : AppCompatActivity(), OnMapReadyCallback,
    CotizacionRepository.onAceptarCotizacionListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySeleccionarUbicacion2Binding

    private lateinit var latitude: String
    private lateinit var longitude: String

    private lateinit var btnEnviar: Button
    private lateinit var txtInstruccion: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latitude = "0"
        longitude = "0"
        binding = ActivitySeleccionarUbicacion2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnEnviar = findViewById(R.id.btnEnviar)
        txtInstruccion = findViewById(R.id.txtInstruccion)

        btnEnviar.setOnClickListener {

            if (txtInstruccion.text.toString().isEmpty()) {
                txtInstruccion.setText("No hay instrucciones")
            }
            val instruccion = Instruccion(latitude, longitude, txtInstruccion.text.toString())
            val id = intent.extras?.get("cotizacionID")
            Toast.makeText(this, "ID: $id", Toast.LENGTH_SHORT).show()
            val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "")
            if (token != null) {
                CotizacionRepository.aceptarCotizacion(id.toString(), token, instruccion, this)
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it).title("Marker in Sydney"))
            longitude = it.longitude.toString()
            latitude = it.latitude.toString()
            println("Longitud: $longitude")
            println("Latitud: $latitude")
        }
    }

    override fun onSuccess(body: Mensaje?) {
        finish()
    }

    override fun onFailure(throwable: Throwable) {
        Toast.makeText(this, "Error al aceptar la cotizacion", Toast.LENGTH_SHORT).show()
    }
}