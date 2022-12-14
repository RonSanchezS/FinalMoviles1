package com.moviles.proyectomoviles

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.activityAvanzada.SeleccionarUbicacion2
import com.moviles.proyectomoviles.adapters.CharlaAdapter
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Mensaje
import com.moviles.proyectomoviles.models.Trabajador
import com.moviles.proyectomoviles.repository.ConversacionRepository
import com.moviles.proyectomoviles.repository.CotizacionRepository
import com.moviles.proyectomoviles.repository.ImageController
import com.moviles.proyectomoviles.repository.LoginRepository
import okhttp3.ResponseBody
import java.lang.Exception

class CotizacionChat : AppCompatActivity(), ConversacionRepository.onConversacionGetListener,
    LoginRepository.onGetDatosDeUsuarioListener, ConversacionRepository.onMensajeSendListener2,
    CotizacionRepository.onRechazarCotizacionListener,
    ConversacionRepository.onProfilePictureUploadListener {

    private lateinit var recyclerCharla: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var txtInput: EditText


    private lateinit var token: String
    private lateinit var id: String

    private lateinit var adapter: CharlaAdapter

    private lateinit var layoutCotizacion: LinearLayout
    private lateinit var btnAceptar: Button
    private lateinit var btnRechazar: Button
    private lateinit var txtPrecioCotizacion: TextView

    private lateinit var imageButton: ImageButton
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                storeImage(result.data?.data!!)
            }
        }

    private fun storeImage(dataUri: Uri) {
        ImageController.saveImage(this, 1, dataUri)
    }

    private lateinit var cotizacionID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_chat)
        setUpContent()
        setUpListeners()

        //set a thread every 5 seconds
//        val thread = Thread {
//            while (true) {
//                Thread.sleep(5000)
//                runOnUiThread {
//
//                }
//            }
//        }
//        thread.start()


    }


    private fun setUpListeners() {
        imageButton.setOnClickListener {
            ImageController.selectPhotoFromGallery(resultLauncher)

        }
        btnEnviar.setOnClickListener {
            if(txtInput.text.isEmpty()){
                sendImage(ImageController.getImage(this))
                return@setOnClickListener
            }
            val mensaje = Mensaje(txtInput.text.toString())
            println("TOKEN: $token mensaje $mensaje id $cotizacionID")
            ConversacionRepository.enviarMensaje(token, mensaje, cotizacionID, this)
            txtInput.setText("")

        }
    }

    private fun sendImage(image: Uri) {
        ConversacionRepository.uploadProfilePicture(cotizacionID, token, image, this)

    }

    private fun setUpContent() {
        recyclerCharla = findViewById(R.id.recyclerCharla)
        btnEnviar = findViewById(R.id.btnSensMessage)
        txtInput = findViewById(R.id.inputMSJText)
        token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "").toString()
        id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("id", "").toString()


        imageButton = findViewById(R.id.imageButton)

        cotizacionID = intent.extras?.get("cotizacionID").toString()


        layoutCotizacion = findViewById(R.id.layoutCotizacion)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnRechazar = findViewById(R.id.btnRechazarCotizacion)
        txtPrecioCotizacion = findViewById(R.id.txtPrecioCotizacion)



        if (intent.extras?.get("precio") != null) {

            layoutCotizacion.visibility = LinearLayout.VISIBLE
            txtPrecioCotizacion.text =
                "El precio ofertado es: ${(intent.extras?.get("precio").toString())}"
            btnAceptar.setOnClickListener {
                val intent = Intent(this, SeleccionarUbicacion2::class.java)
                intent.putExtra("cotizacionID", cotizacionID)
                startActivity(intent)
            }
            btnRechazar.setOnClickListener {
                CotizacionRepository.rechazarCotizacion(cotizacionID, token, this)
            }
        } else {
            layoutCotizacion.visibility = LinearLayout.GONE

        }
        if (intent.extras?.get("estado") != 1) {
            layoutCotizacion.visibility = LinearLayout.GONE
        }
        LoginRepository.getDatosDeUsuario("Bearer $token", this)


    }


    override fun onConversacionGetError(t: Throwable) {
        println(t.message)
    }

    override fun onConversacionGetSuccess(mensajes: List<CharlaItem>) {
        actualizarChat(mensajes)
    }

    private fun actualizarChat(body: List<CharlaItem>) {
        adapter = CharlaAdapter(body as ArrayList<CharlaItem>, id.toInt())
        recyclerCharla.layoutManager = LinearLayoutManager(this)
        recyclerCharla.adapter = adapter
    }

    override fun onErrorUsuario(t: Throwable) {
        //
    }

    override fun onUsuarioEncontrado(body: Trabajador?) {
        id = body?.id.toString()
        ConversacionRepository.getMensajesDeConversacion(
            "Bearer $token",
            cotizacionID,
            this
        )
    }

    override fun onErrorUsuario2(errorBody: ResponseBody?) {
        TODO("Not yet implemented")
    }

    override fun onMensajeSendSuccess(body: CharlaItem) {
        //clear txtInput

        ConversacionRepository.getMensajesDeConversacion("Bearer $token", cotizacionID, this)
        //scroll to the end of the recycler view


    }

    override fun onMensajeSendError(throwable: Throwable) {
        ConversacionRepository.getMensajesDeConversacion("Bearer $token", cotizacionID, this)
        //scroll to the end of the recycler view

    }

    override fun onSuccess(body: Mensaje?) {
        finish()
    }

    override fun onFailure(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onProfilePictureFailed(exception: Exception) {
        ConversacionRepository.getMensajesDeConversacion("Bearer $token", cotizacionID, this)

    }

    override fun onProfilePictureSuccess(body: Mensaje) {
        ConversacionRepository.getMensajesDeConversacion("Bearer $token", cotizacionID, this)

    }


}