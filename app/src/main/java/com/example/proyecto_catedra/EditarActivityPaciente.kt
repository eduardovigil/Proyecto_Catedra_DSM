package com.example.proyecto_catedra

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarActivityPaciente : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelperPaciente
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextDui: EditText
    private lateinit var editTextMascota: EditText
    private lateinit var btnGuardarCambios: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_paciente)

        dbHelper = DatabaseHelperPaciente(this)

        editTextNombre = findViewById(R.id.editTextNombreEditar)
        editTextDireccion = findViewById(R.id.editTextDireccionEditar)
        editTextDui = findViewById(R.id.editTextDuiEditar)
        editTextMascota = findViewById(R.id.editTextMascotaEditar)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        val paciente = intent.getSerializableExtra("paciente") as? Paciente

        if (paciente == null) {
            Toast.makeText(this, "Error: No se pudo obtener la información del paciente", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editTextNombre.setText(paciente.nombre)
        editTextDireccion.setText(paciente.direccion)
        editTextDui.setText(paciente.dui)
        editTextMascota.setText(paciente.mascota)

        btnGuardarCambios.setOnClickListener {

            val nuevoNombre = editTextNombre.text.toString()
            val nuevaDireccion = editTextDireccion.text.toString()
            val nuevoDui = editTextDui.text.toString()
            val nuevaMascota = editTextMascota.text.toString()

            paciente.nombre = nuevoNombre
            paciente.direccion = nuevaDireccion
            paciente.dui = nuevoDui
            paciente.mascota = nuevaMascota

            dbHelper.updatePaciente(paciente)

            setResult(RESULT_OK)

            finish()
        }
    }
}