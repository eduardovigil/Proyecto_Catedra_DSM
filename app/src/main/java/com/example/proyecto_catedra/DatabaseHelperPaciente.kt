package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperPaciente(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PacientesDB"

        private const val TABLE_PACIENTES = "pacientes"
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_DIRECCION = "direccion"
        private const val KEY_DUI = "dui"
        private const val KEY_MASCOTA = "mascota"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_PACIENTES("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NOMBRE TEXT,"
                + "$KEY_DIRECCION TEXT,"
                + "$KEY_DUI TEXT,"
                + "$KEY_MASCOTA TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PACIENTES")
        onCreate(db)
    }

    fun addPaciente(paciente: Paciente): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, paciente.nombre)
        values.put(KEY_DIRECCION, paciente.direccion)
        values.put(KEY_DUI, paciente.dui)
        values.put(KEY_MASCOTA, paciente.mascota)

        val id = db.insert(TABLE_PACIENTES, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllPacientes(): List<Paciente> {
        val pacientesList = mutableListOf<Paciente>()
        val selectQuery = "SELECT * FROM $TABLE_PACIENTES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val paciente = Paciente(
                    cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(KEY_DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(KEY_DUI)),
                    cursor.getString(cursor.getColumnIndex(KEY_MASCOTA))
                )
                pacientesList.add(paciente)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return pacientesList
    }

    fun updatePaciente(paciente: Paciente): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, paciente.nombre)
        values.put(KEY_DIRECCION, paciente.direccion)
        values.put(KEY_DUI, paciente.dui)
        values.put(KEY_MASCOTA, paciente.mascota)

        return db.update(
            TABLE_PACIENTES,
            values,
            "$KEY_ID = ?",
            arrayOf(paciente.id.toString())
        )
    }

    fun deletePaciente(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PACIENTES, "$KEY_ID = ?", arrayOf(id.toString()))
    }
}