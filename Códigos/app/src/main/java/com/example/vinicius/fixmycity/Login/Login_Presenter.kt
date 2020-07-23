package com.example.vinicius.fixmycity.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.vinicius.fixmycity.Banco_de_Dados.AppDataBase
import com.example.vinicius.fixmycity.Banco_de_Dados.DificuldadeDAO
import com.example.vinicius.fixmycity.Entidades.Usuario
import com.example.vinicius.fixmycity.network.RetrofitInitializer
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login_Presenter(val view: Login_Contract.view): Login_Contract.presenter{

    override fun verificaLogin(context: Context){
        val dificuldadeDAO: DificuldadeDAO = AppDataBase.getInstace(context).DificuldadeDAO()
        val usuario : List<Usuario>
        usuario = dificuldadeDAO.getUser()
        view.verificaLoginView(usuario)
    }

    override fun cadastraUsuario(usuario: Usuario, context: Context) {
        val dificuldadeDAO: DificuldadeDAO = AppDataBase.getInstace(context).DificuldadeDAO()
        doAsync {
            dificuldadeDAO.insertUser(usuario)
        }

        val DificuldadeService = RetrofitInitializer().createDificuldadeService()

        val call = DificuldadeService.criaUsuario( usuario.email)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.v("CreateUser", "success")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("CreateUser error:", t.message)
            }
        })

    }
}