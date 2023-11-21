package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarActivityCita : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelperCita
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDui: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var btnGuardarCambios: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cita)

        dbHelper = DatabaseHelperCita(this)

        editTextNombre = findViewById(R.id.editTextNombreEditarC)
        editTextDui = findViewById(R.id.editTextDuiEditarC)
        editTextFecha = findViewById(R.id.editTextFechaEditarC)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        val cita= intent.getSerializableExtra("cita") as? Cita

        if (cita == null) {
            Toast.makeText(this, "Error: No se pudo obtener la información de la cita", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editTextNombre.setText(cita.nombre)
        editTextDui.setText(cita.dui)
        editTextFecha.setText(cita.fecha)

        btnGuardarCambios.setOnClickListener {

            val nuevoNombre = editTextNombre.text.toString()
            val nuevoDui = editTextDui.text.toString()
            val nuevaFecha = editTextFecha.text.toString()

            cita.nombre = nuevoNombre
            cita.dui = nuevoDui
            cita.fecha = nuevaFecha

            dbHelper.updateCita(cita)

            setResult(RESULT_OK)

            finish()
        }
    }
}