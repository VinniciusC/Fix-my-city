package com.example.vinicius.fixmycity.Login

import android.content.Context
import com.example.vinicius.fixmycity.Entidades.Usuario

interface Login_Contract {
    interface view{
        fun verificaLoginView(usuario: List<Usuario>) : Boolean
    }
    interface presenter{
        fun verificaLogin(context: Context)
        fun cadastraUsuario(usuario: Usuario, context: Context)
    }
}