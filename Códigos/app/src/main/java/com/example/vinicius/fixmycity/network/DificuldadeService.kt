package com.example.vinicius.fixmycity.network

import com.example.vinicius.fixmycity.Entidades.DificuldadesList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DificuldadeService {

    companion object{
        private const val API_KEY = "teste_token"
    }

    @GET("/select?token=$API_KEY")
    fun getByID(@Query("id") id : Int ) : Call<DificuldadesList>

    @GET("/select?token=$API_KEY")
    fun getAll() : Call<DificuldadesList>

    @FormUrlEncoded
    @POST("/upload/")
    fun upload(
        @Field("base64") b64 : String, @Field("ImageName") name : String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/delete/?token=$API_KEY")
    fun deleteById(
        @Field("id") id : String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/insertUser/?token=$API_KEY")
    fun criaUsuario(
        @Field("email") email : String ) : Call<ResponseBody>

    @GET("/getUserIDByEmail?token=$API_KEY")
    fun getUserIdByEmail(@Query("email") id : String) : Call<String>

    @FormUrlEncoded
    @POST("/insert/?token=$API_KEY")
    fun insertpost(@Field("userID") id : Int, @Field("categoria") c : String, @Field("status") s : Int,
                  @Field("descricao") d :String, @Field("localizacao") l : String, @Field("locX") x : Double, @Field("locY") y : Double,
                  @Field("data") data:String, @Field("dataTimestamp") stamp: String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/insert/?token=$API_KEY")
    fun update(@Field("userID") id : Int, @Field("categoria") c : String, @Field("status") s : Int,
               @Field("descricao") d :String, @Field("localizacao") l : String, @Field("locX") x : Double, @Field("locY") y : Double,
               @Field("data") data:String, @Field("ID") u : Int, @Field("dataTimestamp") stamp: String, @Field("update") update : Int) : Call<ResponseBody>

}