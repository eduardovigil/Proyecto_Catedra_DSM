package com.example.proyecto_catedra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Toast.makeText(this@MainActivity, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
    }
}