package com.example.proyecto_catedra
import java.io.Serializable

data class Empleado(
    var id: Long = -1,
    var nombre: String,
    var correo: String,
    var direccion: String,
    var dui: String
) : Serializable