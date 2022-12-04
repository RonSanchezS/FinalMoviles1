package com.moviles.proyectomoviles.models

class Worker {
    var id : Int? = null
    var phone : String? = null
    var profilePicture : String? = null
    var reviewAvg : Double? = null
    var user : Usuario? = null
    var categories : List<Categories>? = null
    override fun toString(): String {
        return "Worker(id=$id, phone=$phone, profilePicture=$profilePicture, reviewAvg=$reviewAvg, user=$user, categories=$categories)"
    }

}