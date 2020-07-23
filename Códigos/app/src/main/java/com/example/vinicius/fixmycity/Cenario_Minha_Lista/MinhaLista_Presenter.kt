package com.example.vinicius.fixmycity.Cenario_Minha_Lista

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.vinicius.fixmycity.Banco_de_Dados.AppDataBase
import com.example.vinicius.fixmycity.Banco_de_Dados.DificuldadeDAO
import com.example.vinicius.fixmycity.Cenario_Lista.Lista_Contract
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Entidades.DificuldadesList
import com.example.vinicius.fixmycity.network.DificuldadeService
import com.example.vinicius.fixmycity.network.RetrofitInitializer
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MinhaLista_Presenter(val view: MinhaLista_Contract.view): MinhaLista_Contract.presenter {
    override fun deleteItem(context: Context, dificuldades: Dificuldades) {
        val DificuldadeService = RetrofitInitializer().createDificuldadeService()

        val call = DificuldadeService.deleteById(dificuldades.ID.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.v("Delete", "success")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Delete error:", t.message)
            }
        })
    }

    override fun getIdUser(context: Context) {
        val dificuldadeDAO: DificuldadeDAO = AppDataBase.getInstace(context).DificuldadeDAO()

        doAsync {
            val email = dificuldadeDAO.getEmail()
            val DificuldadeService = RetrofitInitializer().createDificuldadeService()
            val call = DificuldadeService.getUserIdByEmail(email)

            uiThread {
                call.enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        view.getIDUser(response.body()!!.toInt())
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("GetID error:", t.message)
                    }
                })
            }
        }
    }

    override fun onAtualizaLista(context: Context, id : Int) {
        view.showProgressBar()
        doAsync {

            val dificuldadeService = RetrofitInitializer().createDificuldadeService()

            val call : Call<DificuldadesList>

            call = dificuldadeService.getByID(id)

            call.enqueue(object : Callback<DificuldadesList> {
                override fun onResponse(call: Call<DificuldadesList>, response: Response<DificuldadesList>) {
                    if(response.body()!=null)
                        view.exibeLista(response.body()!!.dificuldades)
                }

                override fun onFailure(call: Call<DificuldadesList>, t: Throwable) {
                    Log.e("Falha na conexao: ", t.message)
                    view.hideProgressBar()
                }
            })

        }
    }
}