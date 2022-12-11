package com.moviles.proyectomoviles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.models.CharlaItem

class CharlaAdapter(val data: ArrayList<CharlaItem>, val idLocal: Int) :
    RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder>() {
    class CharlaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre = itemView.findViewById<TextView>(R.id.txtNombre)
        val txtTexto = itemView.findViewById<TextView>(R.id.txtTexto)
        val txtNombreLocal = itemView.findViewById<TextView>(R.id.txtNombreLocal)
        val txtTextoLocal = itemView.findViewById<TextView>(R.id.txtTextoLocal)

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
        return CharlaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharlaAdapter.CharlaViewHolder, position: Int) {
        val item = data[position]

        if(item.userId == idLocal){
            holder.txtNombreLocal.text = item.user?.name
            holder.txtTextoLocal.text = item.message
            holder.txtNombre.visibility = View.GONE
            holder.txtTexto.visibility = View.GONE
        }else{
            holder.txtNombre.text = item.user?.name
            holder.txtTexto.text = item.message
            holder.txtNombreLocal.visibility = View.GONE
            holder.txtTextoLocal.visibility = View.GONE
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }
}