package com.moviles.proyectomoviles.models

import com.google.gson.annotations.SerializedName

class Cotizacion  (
    @SerializedName("id"                ) var id                : Int?      = null,
    @SerializedName("client_id"         ) var clientId          : Int?      = null,
    @SerializedName("worker_id"         ) var workerId          : Int?      = null,
    @SerializedName("category_id"       ) var categoryId        : Int?      = null,
    @SerializedName("status"            ) var status            : Int?      = null,
    @SerializedName("priceOffer"        ) var priceOffer        : String?   = null,
    @SerializedName("review"            ) var review            : String?   = null,
    @SerializedName("deliveryLatitude"  ) var deliveryLatitude  : String?   = null,
    @SerializedName("deliveryLongitude" ) var deliveryLongitude : String?   = null,
    @SerializedName("deliveryAddress"   ) var deliveryAddress   : String?   = null,
    @SerializedName("client"            ) var client            : Worker?   = Worker(),
    @SerializedName("worker"            ) var worker            : Worker?   = Worker(),
    @SerializedName("category"          ) var category          : Categories? = Categories()
        ){
    override fun toString(): String {
        return "Cotizacion(id=$id, clientId=$clientId, workerId=$workerId, categoryId=$categoryId, status=$status, priceOffer=$priceOffer, review=$review, deliveryLatitude=$deliveryLatitude, deliveryLongitude=$deliveryLongitude, deliveryAddress=$deliveryAddress, client=$client, worker=$worker, category=$category)"
    }
}

