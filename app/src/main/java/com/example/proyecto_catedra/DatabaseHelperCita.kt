package com.example.proyecto_catedra

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelperCita(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CitasDB"

        private const val TABLE_CITAS= "citas"
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_DUI = "dui"
        private const val KEY_FECHA = "fecha"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_CITAS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NOMBRE TEXT,"
                + "$KEY_DUI TEXT,"
                + "$KEY_FECHA TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CITAS")
        onCreate(db)
    }

    fun addCita(cita: Cita): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, cita.nombre)
        values.put(KEY_DUI, cita.dui)
        values.put(KEY_FECHA, cita.fecha)

        val id = db.insert(TABLE_CITAS, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllCitas(): List<Cita> {
        val citasList = mutableListOf<Cita>()
        val selectQuery = "SELECT * FROM $TABLE_CITAS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val cita = Cita(
                    cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(KEY_DUI)),
                    cursor.getString(cursor.getColumnIndex(KEY_FECHA))
                )
                citasList.add(cita)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return citasList
    }

    fun updateCita(cita: Cita): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NOMBRE, cita.nombre)
        values.put(KEY_DUI, cita.dui)
        values.put(KEY_FECHA, cita.fecha)

        return db.update(
            TABLE_CITAS,
            values,
            "$KEY_ID = ?",
            arrayOf(cita.id.toString())
        )
    }

    fun deleteCita(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_CITAS, "$KEY_ID = ?", arrayOf(id.toString()))
    }
}