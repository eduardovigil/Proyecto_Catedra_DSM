package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperEmpleado(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EmpleadosDB"

        private const val TABLE_EMPLEADOS = "empleados"
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_DIRECCION = "direccion"
        private const val KEY_DUI = "dui"
        private const val KEY_CORREO = "correo"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_EMPLEADOS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NOMBRE TEXT,"
                + "$KEY_DIRECCION TEXT,"
                + "$KEY_DUI TEXT,"
                + "$KEY_CORREO TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLEADOS")
        onCreate(db)
    }

    fun addEmpleado(empleado: Empleado): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, empleado.nombre)
        values.put(KEY_DIRECCION, empleado.direccion)
        values.put(KEY_DUI, empleado.dui)
        values.put(KEY_CORREO, empleado.correo)

        val id = db.insert(TABLE_EMPLEADOS, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllEmpleado(): List<Empleado> {
        val empleadosList = mutableListOf<Empleado>()
        val selectQuery = "SELECT * FROM $TABLE_EMPLEADOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val empleado = Empleado(
                    cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(KEY_DIRECCION)),
                    cursor.getString(cursor.getColumnIndex(KEY_DUI)),
                    cursor.getString(cursor.getColumnIndex(KEY_CORREO))
                )
                empleadosList.add(empleado)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return empleadosList
    }

    fun updateEmpleado(empleado: Empleado): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, empleado.nombre)
        values.put(KEY_DIRECCION, empleado.direccion)
        values.put(KEY_DUI, empleado.dui)
        values.put(KEY_CORREO, empleado.correo)

        return db.update(
            TABLE_EMPLEADOS,
            values,
            "$KEY_ID = ?",
            arrayOf(empleado.id.toString())
        )
    }

    fun deleteEmpleado(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_EMPLEADOS, "$KEY_ID = ?", arrayOf(id.toString()))
    }
}