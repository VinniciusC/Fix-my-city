package com.example.vinicius.fixmycity.Cenario_Cadastro

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.vinicius.fixmycity.Banco_de_Dados.AppDataBase
import com.example.vinicius.fixmycity.Banco_de_Dados.DificuldadeDAO
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.network.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream


class Cadastro_Presenter(val view: Cadastro_Contract.view): Cadastro_Contract.presenter{

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

    override fun uploadImage(bitmap: Bitmap, id : String) {

        doAsync {
            val byteArrayOutputStream = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)

            val encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

            val DificuldadeService = RetrofitInitializer().createDificuldadeService()

            val call = DificuldadeService.upload(encodeImage, id + ".jpg")

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.v("Upload", "success")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Upload error:", t.message)
                }
            })
        }
    }


    override fun onSalvaDificuldade(context: Context, Dificuldade: Dificuldades) : Boolean{
        var deuCerto : Boolean = false
        val DificuldadeService = RetrofitInitializer().createDificuldadeService()

        val call = DificuldadeService.insertpost( Dificuldade.userID!!, Dificuldade.categoria, Dificuldade.status, Dificuldade.descricao, Dificuldade.localizacao, Dificuldade.locX,
                                                Dificuldade.locY, Dificuldade.data, Dificuldade.dataTimestamp)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.v("Insert", "success")
                deuCerto = true
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Insert error:", t.message)
                deuCerto = false
            }
        })
        return deuCerto
    }

    override fun onEditaDificuldade(context: Context, Dificuldade: Dificuldades) : Boolean{

        var deuCerto : Boolean = false
        val DificuldadeService = RetrofitInitializer().createDificuldadeService()

        val call = DificuldadeService.update(Dificuldade.userID!!, Dificuldade.categoria, Dificuldade.status, Dificuldade.descricao, Dificuldade.localizacao, Dificuldade.locX,
            Dificuldade.locY, Dificuldade.data, Dificuldade.ID!!, Dificuldade.dataTimestamp, 1)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.v("Edit", "success")
                deuCerto = true
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Edit error:", t.message)
            }
        })
        return deuCerto

    }
}
