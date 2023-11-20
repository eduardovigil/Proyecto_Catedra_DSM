package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarActivityEmpleado : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelperEmpleado
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextDui: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var btnGuardarCambios: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empleado)

        dbHelper = DatabaseHelperEmpleado(this)

        editTextNombre = findViewById(R.id.editTextNombreEditarE)
        editTextDireccion = findViewById(R.id.editTextDireccionEditarE)
        editTextDui = findViewById(R.id.editTextDuiEditarE)
        editTextCorreo = findViewById(R.id.editTextCorreoEditar)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosE)

        val empleado= intent.getSerializableExtra("empleado") as? Empleado

        if (empleado == null) {
            Toast.makeText(this, "Error: No se pudo obtener la información del empleado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        editTextNombre.setText(empleado.nombre)
        editTextDireccion.setText(empleado.direccion)
        editTextDui.setText(empleado.dui)
        editTextCorreo.setText(empleado.correo)

        btnGuardarCambios.setOnClickListener {

            val nuevoNombre = editTextNombre.text.toString()
            val nuevaDireccion = editTextDireccion.text.toString()
            val nuevoDui = editTextDui.text.toString()
            val nuevaCorreo = editTextCorreo.text.toString()

            empleado.nombre = nuevoNombre
            empleado.direccion = nuevaDireccion
            empleado.dui = nuevoDui
            empleado.correo = nuevaCorreo

            dbHelper.updateEmpleado(empleado)

            setResult(RESULT_OK)

            finish()
        }
    }
}