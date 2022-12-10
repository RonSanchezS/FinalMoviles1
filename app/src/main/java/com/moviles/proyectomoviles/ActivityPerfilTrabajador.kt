package com.moviles.proyectomoviles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.moviles.proyectomoviles.models.Trabajo
import com.moviles.proyectomoviles.repository.CotizacionRepository


class ActivityPerfilTrabajador : AppCompatActivity(),
    CotizacionRepository.onPostCotizacionListener {

    private lateinit var workerID : String
    private lateinit var  workerName : String
    private lateinit var  workerPhone : String
    private lateinit var  categoryName : String
    private lateinit var  workerImgLink : String

    private lateinit var  categoryID : String

    private lateinit var btnLlamar : Button
    private lateinit var btnCotizar: Button

    private lateinit var imgTrabajador: ImageView
    private lateinit var lblNombreDeCategoria : TextView
    private lateinit var lblNombreTrabajador : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_trabajador)
        setUpIntentValues()
        setUpListView()
        setUpListeners()


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


    }

    private fun setUpListeners() {
        btnCotizar.setOnClickListener {
            val intent = Intent(this, CotizacionChat::class.java)
            intent.putExtra("workerID", workerID)
            intent.putExtra("cotizacionID", categoryID)
            val token = getSharedPreferences("token", MODE_PRIVATE).getString("token", "").toString()
            val trabajo = Trabajo(workerID, categoryID)
            CotizacionRepository.postCotizacion(trabajo, "Bearer $token", this)

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
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + workerPhone))
                    startActivity(intent)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setUpIntentValues() {
        workerID = intent.extras?.get("workerID").toString()
        workerName = intent.extras?.getString("workerName") ?: ""
        workerPhone = intent.extras?.getString("workerPhone") ?: ""
        categoryName = intent.extras?.getString("categoryName") ?: ""
        workerImgLink = intent.extras?.getString("workerImgLink") ?: ""
        categoryID = intent.extras?.get("categoryID").toString()
        Toast.makeText(this, "categoryID : $categoryID", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(t: Throwable) {
        Toast.makeText(this, "Error al iniciar el chat de cotizar", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(body: Trabajo?) {
        finish()
    }
}