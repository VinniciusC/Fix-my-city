package com.example.vinicius.fixmycity.Banco_de_Dados

import android.arch.persistence.room.*
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Entidades.Usuario

@Dao
interface DificuldadeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser (user : Usuario)

    @Query ("SELECT * FROM Usuario")
    fun getUser() : List<Usuario>

    @Query ("SELECT email FROM Usuario LIMIT 1")
    fun getEmail() : String

}