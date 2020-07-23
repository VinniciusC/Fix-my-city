package com.example.vinicius.fixmycity.Cenario_Lista

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.vinicius.fixmycity.Cenario_Cadastro.Cadastra_Localizacao
import com.example.vinicius.fixmycity.Cenario_Detalhes.Detalhes
import com.example.vinicius.fixmycity.Cenario_Minha_Lista.MinhaLista
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.R
import com.example.vinicius.fixmycity.Utils.GlideApp
import kotlinx.android.synthetic.main.activity_lista_dificuldades.*

class ListaDificuldades : AppCompatActivity(), Lista_Contract.view {

    lateinit var listaDificuldade : List<Dificuldades>
        var presenter: Lista_Contract.presenter = Lista_Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_dificuldades)

        presenter.onAtualizaLista(this)

        GlideApp.with(this)
            .load("https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif")
            .into(loadingGif)

        NovaDificuldade.setOnClickListener{
            val cadastrar = Intent(this, Cadastra_Localizacao::class.java)
            startActivity(cadastrar)
        }
        MinhaListaBtn.setOnClickListener {
            val minhaLista = Intent(this, MinhaLista::class.java)
            startActivity(minhaLista)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onAtualizaLista(this)
    }

    fun itemClique(dificuldade:Dificuldades){
        val EditaDificuldadeIntent = Intent(this, Detalhes::class.java)
        EditaDificuldadeIntent.putExtra("dificuldade",dificuldade)
        startActivity(EditaDificuldadeIntent)
    }

    override fun exibeLista(lista: List<Dificuldades>) {

        listaDificuldade = lista
        val adapter = DificuldadeAdapter(this, listaDificuldade, {dificuldadeItem : Dificuldades -> itemClique(dificuldadeItem) } )
        val layoutManager = LinearLayoutManager(this)
        //val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        hideProgressBar()

        RvDificuldades.adapter = adapter
        RvDificuldades.layoutManager = layoutManager
        //RvDificuldades.addItemDecoration(dividerItemDecoration)


    }
    override fun hideProgressBar() {
        loading.setVisibility(View.GONE)
    }

    override fun showProgressBar() {
        loading.setVisibility(View.VISIBLE)
    }

}


