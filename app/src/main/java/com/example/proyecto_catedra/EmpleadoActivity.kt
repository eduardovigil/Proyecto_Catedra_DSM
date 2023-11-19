package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EmpleadoActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelperEmpleado
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empleado)

        dbHelper = DatabaseHelperEmpleado(this)

        val editTextNombreE = findViewById<EditText>(R.id.editTextNombreE)
        val editTextDireccionE = findViewById<EditText>(R.id.editTextDireccionE)
        val editTextDuiE = findViewById<EditText>(R.id.editTextDuiE)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)

        val btnAgregar = findViewById<Button>(R.id.btnAgregarE)

        btnAgregar.setOnClickListener {
            val nombre = editTextNombreE.text.toString()
            val direccion = editTextDireccionE.text.toString()
            val dui = editTextDuiE.text.toString()
            val correo = editTextCorreo.text.toString()

            val empleado = Empleado(nombre = nombre, direccion = direccion, dui = dui, correo = correo)
            dbHelper.addEmpleado(empleado)
            actualizarListaEmpleados()

            editTextNombreE.text.clear()
            editTextDireccionE.text.clear()
            editTextDuiE.text.clear()
            editTextCorreo.text.clear()
        }

        val listViewPacientes = findViewById<ListView>(R.id.listViewEmpleado)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listViewPacientes.adapter = adapter

        listViewPacientes.setOnItemClickListener { _, _, position, _ ->
            val empleado = dbHelper.getAllEmpleado()[position]

            val opciones = arrayOf("Editar", "Eliminar")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona una opción")
            builder.setItems(opciones) { _, which ->
                when (which) {
                    0 -> editarEmpleado(empleado)
                    1 -> eliminarEmpleado(empleado.id)
                }
            }
            builder.show()
        }
        actualizarListaEmpleados()
    }

    private fun actualizarListaEmpleados() {
        val pacientes = dbHelper.getAllEmpleado().map { empleado ->
            "ID: ${empleado.id}\n" +
                    "Nombre: ${empleado.nombre}\n" +
                    "Dirección: ${empleado.direccion}\n" +
                    "DUI: ${empleado.dui}\n" +
                    "Correo: ${empleado.correo}"
        }
        adapter.clear()
        adapter.addAll(pacientes)
        adapter.notifyDataSetChanged()
    }

    private fun editarEmpleado(empleado: Empleado) {
        val intent = Intent(this, EditarActivityEmpleado::class.java)
        intent.putExtra("empleado", empleado)
        editarEmpleadoLauncher.launch(intent)

    }

    private fun eliminarEmpleado(empleadoId: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que quieres eliminar este empleado?")
        builder.setPositiveButton("Sí") { _, _ ->
            dbHelper.deleteEmpleado(empleadoId)
            actualizarListaEmpleados()
            Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.show()
    }

    private val editarEmpleadoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            actualizarListaEmpleados()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuopciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.opcion1) {
            Toast.makeText(this, getString(R.string.pacientes), Toast.LENGTH_LONG).show();
            val intent = Intent(this, PacienteActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion2) {
            Toast.makeText(this, getString(R.string.empleados), Toast.LENGTH_LONG).show();
            val intent = Intent(this, EmpleadoActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion3) {
            Toast.makeText(this, getString(R.string.citas), Toast.LENGTH_LONG).show();
            val intent = Intent(this, CitaActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}