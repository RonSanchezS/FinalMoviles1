package com.moviles.proyectomoviles.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviles.proyectomoviles.R
import com.moviles.proyectomoviles.models.Categories

class CategoryAdapter(val data: ArrayList<Categories>, val listener :  onCategoryClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    interface onCategoryClickListener {
        fun onCategoryClick(category: Categories)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName = itemView.findViewById<TextView>(R.id.categoryName)
        val imagen = itemView.findViewById<ImageView>(R.id.imageView)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
      val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = data[position]
        //add listener
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(category)
        }
        holder.categoryName.text = category.name
        Glide
            .with(holder.itemView.context)
            .load(category.icon)
            .centerCrop()
            .into(holder.imagen)

    }


}