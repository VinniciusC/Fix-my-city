package com.example.vinicius.fixmycity.Entidades

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.File
import java.io.Serializable
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
class Dificuldades(
    var categoria: String,
    var status: Int = 1,
    var descricao: String,
    var localizacao: String,
    var locX : Double,
    var locY : Double,
    var data: String,
    var dataTimestamp: String,
    val ID: Int? = null,
    val userID: Int? = null
) :Serializable