package com.moviles.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moviles.proyectomoviles.databinding.ActivitySeleccionarUbicacionParaChatBinding
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Location
import com.moviles.proyectomoviles.models.Ubicacion
import com.moviles.proyectomoviles.repository.ConversacionRepository

class SeleccionarUbicacionParaChat : AppCompatActivity(), OnMapReadyCallback,
    ConversacionRepository.onUbicacionListener {

    private lateinit var mMap: GoogleMap
    private lateinit var btnSend: Button
    private lateinit var binding: ActivitySeleccionarUbicacionParaChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeleccionarUbicacionParaChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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
        btnSend = findViewById(R.id.btnSend)
        var latitude = sydney.latitude
        var longitude = sydney.longitude
        //add onMapClickListener
        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng))
            latitude = latLng.latitude
            longitude = latLng.longitude
        }
        btnSend.setOnClickListener {
            val id = intent.extras?.get("id").toString()
            val location = Location(latitude.toString(), longitude.toString())
            val ubi = Ubicacion(location)
            val token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "")
            Toast.makeText(this, "Enviando token $token", Toast.LENGTH_SHORT).show()
            if (token != null) {
                ConversacionRepository.uploadLocation(ubi, token, id, this)
            }
        }
    }

    override fun onUbicacionSuccess(body: CharlaItem) {
       TODO()
    }

    override fun onUbicacionError(throwable: Throwable) {
        println(throwable)
    }
}