package com.moviles.proyectomoviles.models

class Mensaje(
    var message : String = ""
) {
    override fun toString(): String {
        return "Mensage(message='$message')"
    }
}