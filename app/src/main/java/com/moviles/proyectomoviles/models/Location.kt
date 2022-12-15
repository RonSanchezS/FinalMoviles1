package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class Location(
    @SerializedName("latitude"  ) var latitude  : String? = null,
    @SerializedName("longitude" ) var longitude : String? = null
){

}