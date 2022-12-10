package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class CharlaItem(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("work_id"    ) var workId    : Int?    = null,
    @SerializedName("user_id"    ) var userId    : Int?    = null,
    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("latitude"   ) var latitude  : String? = null,
    @SerializedName("longitude"  ) var longitude : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("image"      ) var image     : String? = null,
    @SerializedName("user"       ) var user      : Trabajador?   = Trabajador()
) {
}