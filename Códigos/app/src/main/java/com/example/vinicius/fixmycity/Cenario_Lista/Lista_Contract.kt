package com.example.vinicius.fixmycity.Cenario_Lista

import android.content.Context
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Entidades.DificuldadesList

interface Lista_Contract {

    interface view{
         fun exibeLista(lista:  List<Dificuldades>)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface presenter{
        fun onAtualizaLista(context: Context)
    }
}