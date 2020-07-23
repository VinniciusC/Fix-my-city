package com.example.vinicius.fixmycity.network

import com.example.vinicius.fixmycity.Entidades.Dificuldades
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://fixmycitymobile.000webhostapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun createDificuldadeService() = retrofit.create(DificuldadeService::class.java)

}