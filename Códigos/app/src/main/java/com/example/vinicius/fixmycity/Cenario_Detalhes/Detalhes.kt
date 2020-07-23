package com.example.vinicius.fixmycity.Cenario_Detalhes

import android.os.Bundle
import android.app.Activity
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.Utils.GlideApp
import com.example.vinicius.fixmycity.R

import kotlinx.android.synthetic.main.activity_detalhes.*
import java.util.*
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.request.RequestOptions






class Detalhes : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val dificuldade : Dificuldades = intent.getSerializableExtra("dificuldade") as Dificuldades

        categoriaTxt.text = dificuldade.categoria
        if(dificuldade.status == 1){
            status.text = "Pendente"
        }
        else{
            status.text = "Resolvido"
        }
        desc.text = dificuldade.descricao

        val caminho = "https://fixmycitymobile.000webhostapp.com/fotos/" +  dificuldade.dataTimestamp + ".jpg"

        val thumbnail = GlideApp.with(this)
            .load(R.drawable.item_sem_imagem)
            .apply(RequestOptions().circleCrop())

        GlideApp.with(this)
            .load(caminho)
            .thumbnail(thumbnail) //fim da gabiarra
            .centerCrop()
            .into(imageView)

        verMaps.setOnClickListener {
            val uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%s)", dificuldade.locX, dificuldade.locY,
                dificuldade.localizacao, dificuldade.categoria)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

    }


}
