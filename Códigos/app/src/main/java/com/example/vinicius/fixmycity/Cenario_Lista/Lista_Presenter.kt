package com.example.vinicius.fixmycity.Cenario_Lista

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.vinicius.fixmycity.Banco_de_Dados.AppDataBase
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Entidades.DificuldadesList
import com.example.vinicius.fixmycity.network.RetrofitInitializer
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Lista_Presenter(val view: Lista_Contract.view):Lista_Contract.presenter {

    override fun onAtualizaLista(context: Context) {
        view.showProgressBar()
        doAsync {
            val dificuldadeService = RetrofitInitializer().createDificuldadeService()

            val call: Call<DificuldadesList>

            call = dificuldadeService.getAll()

            call.enqueue(object : Callback<DificuldadesList> {
                override fun onResponse(call: Call<DificuldadesList>, response: Response<DificuldadesList>) {
                    if (response.body() != null)
                        view.exibeLista(response.body()!!.dificuldades)
                        view.hideProgressBar()
                }

                override fun onFailure(call: Call<DificuldadesList>, t: Throwable) {
                    Log.e("Falha na conexao: ",  t.message)
                    view.hideProgressBar()
                }
            })
        }
    }
}