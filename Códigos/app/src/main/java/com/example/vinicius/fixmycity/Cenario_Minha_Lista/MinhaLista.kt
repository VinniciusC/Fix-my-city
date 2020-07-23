package com.example.vinicius.fixmycity.Cenario_Minha_Lista

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.vinicius.fixmycity.Cenario_Cadastro.Cadastra_Localizacao
import com.example.vinicius.fixmycity.Cenario_Cadastro.Cadastro_Dificuldades
import com.example.vinicius.fixmycity.Cenario_Detalhes.Detalhes
import com.example.vinicius.fixmycity.Cenario_Lista.DificuldadeAdapter
import com.example.vinicius.fixmycity.Cenario_Lista.Lista_Contract
import com.example.vinicius.fixmycity.Cenario_Lista.Lista_Presenter
import com.example.vinicius.fixmycity.Cenario_Lista.MinhaListaAdapter
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.R
import kotlinx.android.synthetic.main.activity_lista_dificuldades.*
import kotlinx.android.synthetic.main.activity_minha_lista.*

class MinhaLista : AppCompatActivity(), MinhaLista_Contract.view {



    lateinit var listaDificuldade : List<Dificuldades>
    var userId = 0
    var presenter: MinhaLista_Contract.presenter = MinhaLista_Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_lista)

        presenter.getIdUser(this)
    }

    override fun onResume() {
        super.onResume()
        if(userId!=0)
            presenter.onAtualizaLista(this, userId)
    }

    fun itemClique(dificuldade: Dificuldades){
        val EditaDificuldadeIntent = Intent(this, Cadastro_Dificuldades::class.java)
        EditaDificuldadeIntent.putExtra("edita", dificuldade)
        startActivity(EditaDificuldadeIntent)
    }
    fun deleteItem(dificuldades : Dificuldades){
        presenter.deleteItem(this, dificuldades)
        presenter.onAtualizaLista(this, userId)
    }

    override fun exibeLista(lista: List<Dificuldades>) {
        semItem.visibility = View.GONE
        listaDificuldade = lista
        val adapter = MinhaListaAdapter(this, listaDificuldade, { dificuldadeItem : Dificuldades -> itemClique(dificuldadeItem) } ,
                                                                                {dificuldadesItem: Dificuldades ->  deleteItem(dificuldadesItem)})
        val layoutManager = LinearLayoutManager(this)


        RvMinhaLista.adapter = adapter
        RvMinhaLista.layoutManager = layoutManager

        hideProgressBar()


    }

    override fun getIDUser(id: Int) {
        presenter.onAtualizaLista(this, id)
        userId = id
    }

    override fun hideProgressBar() {
        loadingM.setVisibility(View.GONE)
    }

    override fun showProgressBar() {
        loadingM.setVisibility(View.VISIBLE)
    }
}
