package com.moviles.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import com.moviles.proyectomoviles.models.Review
import com.moviles.proyectomoviles.repository.CotizacionRepository
import com.moviles.proyectomoviles.repository.ReviewRepository

class ActivityPuntuar : AppCompatActivity() {
    private lateinit var btnCancelarPuntuacion: Button
    private lateinit var btnEnviarPuntuacion: Button
    private lateinit var ratingBar: RatingBar

    private lateinit var estado: String
    private lateinit var idTrabajo: String
    private  var review: Float = 0.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntuar)

        setUpListView()
        setUpIntentExtras()
        SetUpListeners()
        setUpLogic()
        //print all the extras

    }

    private fun setUpLogic() {

        if(estado != "3"){
            btnEnviarPuntuacion.isEnabled = false
        }

        if (review != null) {
            ratingBar.rating = review
        }else{
            ratingBar.rating = 0f
        }
    }

    private fun setUpIntentExtras() {
        estado = intent.extras?.get("estado").toString()
        review = intent?.extras?.get("review").toString().toFloat()
        idTrabajo = intent?.extras?.get("idTrabajo").toString()


    }

    private fun SetUpListeners() {
        btnCancelarPuntuacion.setOnClickListener {
            finish()
        }
        btnEnviarPuntuacion.setOnClickListener {
         //Here we get the ratingBar value
            val rating = ratingBar.rating
            val objetoRating = Review(rating)
            //Here we send the rating to the server
            Toast.makeText(this, "Rating: $objetoRating con trabajo ${idTrabajo.toInt()}" , Toast.LENGTH_SHORT).show()
            ReviewRepository.dejarReview(idTrabajo.toInt(), objetoRating)
            finish()
        }
    }

    private fun setUpListView() {
        btnCancelarPuntuacion = findViewById(R.id.btnCancelarPuntuacion)
        btnEnviarPuntuacion = findViewById(R.id.btnEnviarPuntuacion)
        ratingBar = findViewById(R.id.ratingBar)
    }
}