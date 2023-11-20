package com.example.proyecto_catedra
import java.io.Serializable

data class Cita(
    var id: Long = -1,
    var nombre: String,
    var dui: String,
    var fecha: String,
) : Serializable