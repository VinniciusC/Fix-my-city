package com.example.vinicius.fixmycity.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vinicius.fixmycity.Cenario_Lista.ListaDificuldades
import com.example.vinicius.fixmycity.Entidades.Usuario
import com.example.vinicius.fixmycity.R
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity(), Login_Contract.view {

    val presenter: Login_Contract.presenter = Login_Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.verificaLogin(this)

        btnCadastra.setOnClickListener {
            if(email.text != null) {
                val usuario = Usuario (email.text.toString())
                presenter.cadastraUsuario(usuario, this)
                val abreLista = Intent(this, ListaDificuldades::class.java)
                startActivity(abreLista)
                finish()
            }
            else{
                Toast.makeText(this, "Insira um email para continuar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun verificaLoginView(usuario: List<Usuario>): Boolean {
        var login = false
        if(usuario.isNotEmpty()){
            val abreLista = Intent(this, ListaDificuldades::class.java)
            startActivity(abreLista)
            finish()
            login = true
        }
        return login
    }


}
