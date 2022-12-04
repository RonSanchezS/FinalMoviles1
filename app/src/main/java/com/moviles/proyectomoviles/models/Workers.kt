package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class Workers(
    @SerializedName("description") var description: String? = null,
    @SerializedName("worker") var worker: Worker? = Worker()
) {

}