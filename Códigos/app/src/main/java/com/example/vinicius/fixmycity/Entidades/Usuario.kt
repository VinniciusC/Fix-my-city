package com.example.vinicius.fixmycity.Entidades

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.provider.ContactsContract
import java.io.Serializable

@Entity
class Usuario (
    var email: String,

    @PrimaryKey(autoGenerate = true)
    var userID : Int = 0
): Serializable