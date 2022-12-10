package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

data class Trabajador(
    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("email" ) var email : String? = null
) {

}