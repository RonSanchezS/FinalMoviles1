package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.activityAvanzada.SeleccionarUbicacion
import com.moviles.proyectomoviles.activityAvanzada.SeleccionarUbicacion2
import com.moviles.proyectomoviles.adapters.CharlaAdapter
import com.moviles.proyectomoviles.adapters.CotizacionAdapter
import com.moviles.proyectomoviles.models.CharlaItem
import com.moviles.proyectomoviles.models.Mensaje
import com.moviles.proyectomoviles.models.Trabajador
import com.moviles.proyectomoviles.models.Trabajo
import com.moviles.proyectomoviles.repository.ConversacionRepository
import com.moviles.proyectomoviles.repository.CotizacionRepository
import com.moviles.proyectomoviles.repository.LoginRepository
import okhttp3.ResponseBody

class CotizacionChat : AppCompatActivity(), ConversacionRepository.onConversacionGetListener,
    LoginRepository.onGetDatosDeUsuarioListener, ConversacionRepository.onMensajeSendListener2,
    CotizacionRepository.onRechazarCotizacionListener {

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

    private lateinit var cotizacionID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotizacion_chat)
        setUpContent()
        setUpListeners()


    }

    private fun setUpListeners() {
        btnEnviar.setOnClickListener {
            var mensaje = Mensaje(txtInput.text.toString())
            println("TOKEN: $token mensaje $mensaje id $cotizacionID")
            ConversacionRepository.enviarMensaje(token, mensaje, cotizacionID, this)
            txtInput.setText("")
        }
    }

    private fun setUpContent() {
        recyclerCharla = findViewById(R.id.recyclerCharla)
        btnEnviar = findViewById(R.id.btnSensMessage)
        txtInput = findViewById(R.id.inputMSJText)
        token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "").toString()
        id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("id", "").toString()

        cotizacionID = intent.extras?.get("cotizacionID").toString()


        layoutCotizacion = findViewById(R.id.layoutCotizacion)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnRechazar = findViewById(R.id.btnRechazarCotizacion)
        txtPrecioCotizacion = findViewById(R.id.txtPrecioCotizacion)



        if (intent.extras?.get("precio") != null) {

            layoutCotizacion.visibility = LinearLayout.VISIBLE
            txtPrecioCotizacion.text =
                "El precio ofertado es: " + intent.extras?.get("precio").toString()
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
        if (intent.extras?.get("estado")!=1){
            layoutCotizacion.visibility = LinearLayout.GONE
        }
        LoginRepository.getDatosDeUsuario("Bearer $token", this)


    }


    override fun onConversacionGetError(t: Throwable) {
        println(t.message)
    }

    override fun onConversacionGetSuccess(body: List<CharlaItem>) {
        actualizarChat(body)
    }

    private fun actualizarChat(body: List<CharlaItem>) {
        adapter = CharlaAdapter(body as ArrayList<CharlaItem>, id.toInt())
        recyclerCharla.layoutManager = LinearLayoutManager(this)
        recyclerCharla.adapter = adapter
    }

    override fun onErrorUsuario(t: Throwable) {
        TODO("Not yet implemented")
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
        txtInput.setText("xd")

        ConversacionRepository.getMensajesDeConversacion(
            "Bearer $token",
            cotizacionID,
            this@CotizacionChat
        )


    }

    override fun onMensajeSendError(throwable: Throwable) {
        Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show()
        println(throwable.message)

    }

    override fun onSuccess(body: Mensaje?) {
       finish()
    }

    override fun onFailure(throwable: Throwable) {
        TODO("Not yet implemented")
    }


}