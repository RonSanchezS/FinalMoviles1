package com.moviles.proyectomoviles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class ActivityPerfilTrabajador : AppCompatActivity() {

    private lateinit var workerID : String
    private lateinit var  workerName : String
    private lateinit var  workerPhone : String
    private lateinit var  categoryName : String
    private lateinit var  workerImgLink : String

    private lateinit var btnLlamar : Button
    private lateinit var btnCotizar: Button

    private lateinit var imgTrabajador: ImageView
    private lateinit var lblNombreDeCategoria : TextView
    private lateinit var lblNombreTrabajador : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_trabajador)
        setUpIntentValues()
        setUpListView();


    }

    private fun setUpListView() {
        btnLlamar = findViewById(R.id.btnLlamar)
        btnCotizar = findViewById(R.id.btnCotizar)
        imgTrabajador = findViewById(R.id.imgTrabajador)
        lblNombreDeCategoria = findViewById(R.id.lblNombreDeCategoria)
        lblNombreTrabajador = findViewById(R.id.lblNombreTrabajador)

        lblNombreDeCategoria.text = categoryName
        lblNombreTrabajador.text = workerName

        Glide
            .with(this)
            .load(workerImgLink)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgTrabajador)

     setUpListeners()

    }

    private fun setUpListeners() {
        btnCotizar.setOnClickListener {
            //go to cotizar activity
            val intent = Intent(this, ActivityCotizar::class.java)
            intent.putExtra("workerID", workerID)
            startActivity(intent)
        }
        btnLlamar.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                    startActivity(intent)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setUpIntentValues() {
        workerID = intent.extras?.getString("workerID") ?: ""
        workerName = intent.extras?.getString("workerName") ?: ""
        workerPhone = intent.extras?.getString("workerPhone") ?: ""
        categoryName = intent.extras?.getString("categoryName") ?: ""
        workerImgLink = intent.extras?.getString("workerImgLink") ?: ""
    }
}