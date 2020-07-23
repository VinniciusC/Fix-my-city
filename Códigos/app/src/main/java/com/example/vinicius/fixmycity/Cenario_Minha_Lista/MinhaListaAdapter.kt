package com.example.vinicius.fixmycity.Cenario_Lista

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.vinicius.fixmycity.Entidades.Dificuldades
import com.example.vinicius.fixmycity.R
import com.example.vinicius.fixmycity.Utils.GlideApp
import kotlinx.android.synthetic.main.minha_lista_item.view.*


class MinhaListaAdapter(val context: Context, val item: List<Dificuldades>, val clickListener: (Dificuldades) -> Unit, val deleteListener: (Dificuldades) -> Unit)
    : RecyclerView.Adapter<MinhaListaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.minha_lista_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(context, item[position], clickListener, deleteListener)

    }



    class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindView(context: Context, dificuldade: Dificuldades, clickListener: (Dificuldades) -> Unit, deleteListener: (Dificuldades) -> Unit) {

            itemView.Categoria.text = dificuldade.categoria
            itemView.Distancia.text = dificuldade.localizacao
            itemView.Data.text      = dificuldade.data

            val caminho = "https://fixmycitymobile.000webhostapp.com/fotos/" +  dificuldade.dataTimestamp + ".jpg"
            GlideApp.with(context)
                .load(caminho)
                .circleCrop()
                .into(itemView.ivImage)

            itemView.setOnClickListener{
                clickListener(dificuldade)
            }
            itemView.itemBtn.setOnClickListener{
                deleteListener(dificuldade)
            }
        }
    }

}