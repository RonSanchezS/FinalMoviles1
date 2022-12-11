package com.moviles.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    LoginRepository.onGetDatosDeUsuarioListener, ConversacionRepository.onMensajeSendListener {

    private lateinit var recyclerCharla: RecyclerView

    private lateinit var btnEnviar: ImageButton
    private lateinit var txtInput: EditText


    private lateinit var token: String
    private lateinit var id: String

    private lateinit var  adapter : CharlaAdapter

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
        }
    }

    private fun setUpContent() {
        recyclerCharla = findViewById(R.id.recyclerCharla)
        btnEnviar = findViewById(R.id.btnSensMessage)
        txtInput = findViewById(R.id.inputMSJText)
        token = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("token", "").toString()
        id = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("id", "").toString()

        cotizacionID = intent.extras?.get("cotizacionID").toString()



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
            cotizacionID.toString(),
            this
        )
    }

    override fun onErrorUsuario2(errorBody: ResponseBody?) {
        TODO("Not yet implemented")
    }

    override fun onMensajeSendSuccess(body: CharlaItem) {
        txtInput.setText("")
        adapter.agregarDatos(body)

    }

    override fun onMensajeSendError(throwable: Throwable) {
        txtInput.setText("")

    }


}