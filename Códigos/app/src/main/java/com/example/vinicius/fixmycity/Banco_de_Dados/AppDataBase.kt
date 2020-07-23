package com.example.vinicius.fixmycity.Banco_de_Dados

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.vinicius.fixmycity.Entidades.Usuario

@Database(entities = arrayOf(Usuario::class), version = 1)
public abstract class AppDataBase: RoomDatabase() {
    companion object {

        private val DB_NAME = "Usuario.db"
        private var instance: AppDataBase? = null

        private fun create(context: Context): AppDataBase? {
            return Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME).allowMainThreadQueries().build()
        }

        public fun getInstace(context: Context): AppDataBase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }
    }

    public abstract fun DificuldadeDAO(): DificuldadeDAO
}