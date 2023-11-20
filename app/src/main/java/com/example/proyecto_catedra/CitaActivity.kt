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

class CitaActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelperCita
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cita)

        dbHelper = DatabaseHelperCita(this)

        val editTextNombreC = findViewById<EditText>(R.id.editTextNombreC)
        val editTextDuiC = findViewById<EditText>(R.id.editTextDuiE)
        val editTextFecha = findViewById<EditText>(R.id.editTextCorreo)

        val btnAgregar = findViewById<Button>(R.id.btnAgregarC)

        btnAgregar.setOnClickListener {
            val nombre = editTextNombreC.text.toString()
            val dui = editTextDuiC.text.toString()
            val fecha = editTextFecha.text.toString()

            val cita = Cita(nombre = nombre, dui = dui, fecha = fecha)
            dbHelper.addCita(cita)
            actualizarListaCitas()

            editTextNombreC.text.clear()
            editTextDuiC.text.clear()
            editTextFecha.text.clear()
        }

        val listViewCitas = findViewById<ListView>(R.id.listViewCita)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listViewCitas.adapter = adapter

        listViewCitas.setOnItemClickListener { _, _, position, _ ->
            val cita = dbHelper.getAllCitas()[position]

            val opciones = arrayOf("Editar", "Eliminar")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona una opción")
            builder.setItems(opciones) { _, which ->
                when (which) {
                    0 -> editarCita(cita)
                    1 -> eliminarCita(cita.id)
                }
            }
            builder.show()
        }
        actualizarListaCitas()
    }

    private fun actualizarListaCitas() {
        val citas = dbHelper.getAllCitas().map { cita ->
            "ID: ${cita.id}\n" +
                    "Nombre: ${cita.nombre}\n" +
                    "DUI: ${cita.dui}\n" +
                    "Fecha: ${cita.fecha}"
        }
        adapter.clear()
        adapter.addAll(citas)
        adapter.notifyDataSetChanged()
    }

    private fun editarCita(cita: Cita) {
        val intent = Intent(this, EditarActivityCita::class.java)
        intent.putExtra("cita", cita)
        editarCitaLauncher.launch(intent)

    }

    private fun eliminarCita(citaId: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que quieres eliminar esta cita?")
        builder.setPositiveButton("Sí") { _, _ ->
            dbHelper.deleteCita(citaId)
            actualizarListaCitas()
            Toast.makeText(this, "Cita eliminada", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.show()
    }

    private val editarCitaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            actualizarListaCitas()
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