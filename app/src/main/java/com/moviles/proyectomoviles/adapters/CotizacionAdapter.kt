package com.moviles.proyectomoviles.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviles.proyectomoviles.MapsActivity
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.models.Cotizacion

class CotizacionAdapter(val data : ArrayList<Cotizacion>, val listener : onCotizacionClickListener) :
RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder>()
{

    class CotizacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lblNombreCotizacion = itemView.findViewById<TextView>(R.id.lblNombreCotizacion)
        val lblEstadoCotizacion = itemView.findViewById<TextView>(R.id.lblEstadoCotizacion)
        val imgTrabajadorCotizacion = itemView.findViewById<ImageView>(R.id.imgTrabajadorCotizacion)
        val btnIniciarMapa = itemView.findViewById<ImageButton>(R.id.btnIniciarMapa)
    }

    interface onCotizacionClickListener {
        fun onCotizacionClick(cotizacion: Cotizacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CotizacionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cotizacion_list_item, parent, false)
        return CotizacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CotizacionViewHolder, position: Int) {
        val cotizacion = data[position]
        holder.itemView.setOnClickListener {
            listener.onCotizacionClick(cotizacion)
        }
        holder.lblNombreCotizacion.text = cotizacion.worker?.user?.name ?: "Sin nombre"
        when(cotizacion.status){
            0 -> holder.lblEstadoCotizacion.text = "Pendiente"
            1 -> holder.lblEstadoCotizacion.text = "Ofertada"
            2 -> holder.lblEstadoCotizacion.text = "Aceptada"
            -2 -> holder.lblEstadoCotizacion.text = "Rechazada"
            3 -> holder.lblEstadoCotizacion.text = "Finalizada"
            -3 -> holder.lblEstadoCotizacion.text = "Descartado"
            4 -> holder.lblEstadoCotizacion.text = "Calificado"
        }
        //when click on btnIniciarMapa,
        holder.btnIniciarMapa.setOnClickListener {
            val intent = Intent(holder.itemView.context, MapsActivity::class.java)
            intent.putExtra("latitud", cotizacion.deliveryLatitude)
            intent.putExtra("longitud", cotizacion.deliveryLongitude)
          //  println("latitud: ${cotizacion.deliveryLatitude} longitud: ${cotizacion.deliveryLongitude}" )
          holder.itemView.context.startActivity(intent)
         Toast.makeText(holder.itemView.context, "Iniciar mapa", Toast.LENGTH_SHORT).show()

        }

        Glide.with(holder.itemView.context).load(cotizacion.worker?.profilePicture).into(holder.imgTrabajadorCotizacion)

        //SEGUIR AQUI DESPUES DE  CLASES, ASIGNAR LOS VALORES A LOS ELEMENTOS Y TERMINAR EL ADAPTER CON LOS DEMAS ELEMENTOS

    }

    override fun getItemCount(): Int {
        return data.size
    }
}