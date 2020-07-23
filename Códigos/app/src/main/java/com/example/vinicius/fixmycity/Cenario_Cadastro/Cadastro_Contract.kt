package com.example.vinicius.fixmycity.Cenario_Cadastro

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Entidades.Usuario
import java.io.File

interface Cadastro_Contract {

    interface view{
        fun cadastrado()
        fun getIDUser(id : Int) : Int
    }

    interface presenter{
        fun uploadImage(bitmap: Bitmap, id : String)
        fun onSalvaDificuldade(context: Context, Dificuldade: Dificuldades) : Boolean
        fun onEditaDificuldade(context: Context, Dificuldade: Dificuldades) : Boolean
        fun getIdUser(context: Context)
    }
}