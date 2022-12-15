package com.moviles.proyectomoviles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.models.Workers

class WorkerInsideCategoryAdapter(val data: ArrayList<Workers>, val listener: onWorkerListener) :
    RecyclerView.Adapter<WorkerInsideCategoryAdapter.WorkerViewHolder>() {
    class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreWorker = itemView.findViewById<TextView>(R.id.txtNombreWorker)
        val iconWorker = itemView.findViewById<ImageView>(R.id.iconImageWorker)

    }

    interface onWorkerListener {
        fun onWorkerClick(worker: Workers)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = data[position]
        holder.itemView.setOnClickListener {
            listener.onWorkerClick(worker)
        }
        holder.txtNombreWorker.text = worker.worker?.user?.name?: "Sin nombre";
        Glide
            .with(holder.itemView.context)
            .load(worker.worker?.profilePicture)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.iconWorker)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_worker, parent, false)
    return WorkerViewHolder(view)


    }

}