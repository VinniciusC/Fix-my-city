package com.example.vinicius.fixmycity.Cenario_Minha_Lista

import android.content.Context
import com.example.vinicius.fixmycity.Entidades.Dificuldades

interface MinhaLista_Contract {

    interface view{
        fun exibeLista(lista: List<Dificuldades>)
        fun getIDUser(id : Int)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface presenter{
        fun onAtualizaLista(context: Context, id : Int)
        fun getIdUser(context: Context)
        fun deleteItem(context: Context, dificuldades: Dificuldades)
    }
}