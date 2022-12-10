package com.moviles.proyectomoviles.models

class Trabajo(
    var worker_id: String,
    var category_id: String
) {
    override fun toString(): String {
        return "Trabajo(worker_id='$worker_id', category_id='$category_id')"
    }
}