package com.moviles.proyectomoviles.models

class Usuario(
    var email : String,
    var password : String,
    var notification_id : String,

) {
    var name : String = ""
    override fun toString(): String {
        return "Usuario(email='$email', password='$password', notification_id='$notification_id', name='$name')"
    }

}