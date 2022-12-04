package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class Categories {
    var id: Int = 0
    var name: String = ""
    var icon: String = ""
    @SerializedName("workers" ) var workers : ArrayList<Workers> = arrayListOf()
    override fun toString(): String {
        return "Categories(id=$id, name='$name', icon='$icon', workers=$workers)"
    }

}