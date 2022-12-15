package com.moviles.proyectomoviles.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moviles.proyectomoviles.FullScreenImage
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.activityAvanzada.VerUbicacion
import com.moviles.proyectomoviles.models.CharlaItem

class CharlaAdapter(val data: ArrayList<CharlaItem>, val idLocal: Int, val listener : onClickListenerChat) :
    RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder>() {
    class CharlaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre = itemView.findViewById<TextView>(R.id.txtNombre)
        val txtTexto = itemView.findViewById<TextView>(R.id.txtTexto)
        val txtNombreLocal = itemView.findViewById<TextView>(R.id.txtNombreLocal)
        val txtTextoLocal = itemView.findViewById<TextView>(R.id.txtTextoLocal)

        val imagenEntrante = itemView.findViewById<ImageView>(R.id.imagenEntrante)
        val imagenSaliente = itemView.findViewById<ImageView>(R.id.imagenSaliente)
        val mapaEntrante = itemView.findViewById<MapView>(R.id.mapaEntrante)
        val mapaSaliente = itemView.findViewById<MapView>(R.id.mapaSaliente)

    }

    fun agregarDatos(charla: CharlaItem) {
        data.add(charla)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharlaAdapter.CharlaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chat, parent, false)
        //add onClickListener

        return CharlaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharlaAdapter.CharlaViewHolder, position: Int) {
        val itemCharla = data[position]
        holder.itemView.setOnClickListener {
            listener.onMapClick(data[position])
        }
        if (itemCharla.userId == idLocal) {
            if (itemCharla.latitude != null && itemCharla.longitude != null) {
                holder.mapaSaliente.visibility = View.VISIBLE
                //setUpMapaSaliente
                holder.mapaSaliente.onCreate(null)
                holder.mapaSaliente.onResume()
                holder.mapaSaliente.getMapAsync {
                    val latLng =
                        LatLng(itemCharla.latitude!!.toDouble(), itemCharla.longitude!!.toDouble())
                    it.addMarker(MarkerOptions().position(latLng))
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                holder.mapaSaliente.setOnClickListener {
                  listener.onMapClick(itemCharla)
                }

            } else {
                holder.mapaSaliente.visibility = View.GONE
            }
            if (itemCharla.image != null) {
                holder.imagenSaliente.visibility = View.VISIBLE
                holder.imagenSaliente.setOnClickListener {
                    val intent = Intent(holder.itemView.context, FullScreenImage::class.java)
                    intent.putExtra("image", itemCharla.image)
                    holder.itemView.context.startActivity(intent)
                }
                Glide
                    .with(holder.itemView.context)
                    .load(itemCharla.image)
                    .centerCrop()
                    .into(holder.imagenSaliente)
            } else {
                holder.imagenSaliente.visibility = View.GONE
            }
            holder.txtNombreLocal.text = itemCharla.user?.name
            holder.txtTextoLocal.text = itemCharla.message

            ocultarEntrantes(holder)
        } else {
            if (itemCharla.latitude != null && itemCharla.longitude != null) {
                holder.mapaEntrante.visibility = View.VISIBLE
                //setUpMapaEntrante
                holder.mapaEntrante.onCreate(null)
                holder.mapaEntrante.onResume()
                holder.mapaEntrante.getMapAsync {
                    val latLng =
                        LatLng(itemCharla.latitude!!.toDouble(), itemCharla.longitude!!.toDouble())
                    it.addMarker(MarkerOptions().position(latLng))
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                holder.mapaEntrante.setOnClickListener {
                    listener.onMapClick(itemCharla)
                }
            } else {
                holder.mapaEntrante.visibility = View.GONE
            }
            if (itemCharla.image != null) {
                holder.imagenEntrante.visibility = View.VISIBLE
                holder.imagenEntrante.setOnClickListener {
                    val intent = Intent(holder.itemView.context, FullScreenImage::class.java)
                    intent.putExtra("image", itemCharla.image)
                    holder.itemView.context.startActivity(intent)
                }
                Glide
                    .with(holder.itemView.context)
                    .load(itemCharla.image)
                    .centerCrop()
                    .into(holder.imagenEntrante)
            } else {
                holder.imagenEntrante.visibility = View.GONE
            }
            holder.txtNombre.text = itemCharla.user?.name
            holder.txtTexto.text = itemCharla.message

            ocultarSalientes(holder)
        }

    }

    interface onClickListenerChat {
        fun onMapClick(itemCharla: CharlaItem)

    }

    private fun ocultarEntrantes(holder: CharlaAdapter.CharlaViewHolder) {

        holder.txtNombre.visibility = View.GONE
        holder.txtTexto.visibility = View.GONE
        holder.imagenEntrante.visibility = View.GONE
        holder.mapaEntrante.visibility = View.GONE

    }

    private fun ocultarSalientes(holder: CharlaAdapter.CharlaViewHolder) {

        holder.txtNombreLocal.visibility = View.GONE
        holder.txtTextoLocal.visibility = View.GONE
        holder.imagenSaliente.visibility = View.GONE
        holder.mapaSaliente.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }
}