package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class Ubicacion(
    @SerializedName("location" ) var location : Location? = Location()

) {
}