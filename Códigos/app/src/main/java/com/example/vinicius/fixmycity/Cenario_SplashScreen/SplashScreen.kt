package com.example.vinicius.fixmycity.Cenario_SplashScreen

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.vinicius.fixmycity.Cenario_Lista.ListaDificuldades
import com.example.vinicius.fixmycity.Login.Login

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            startActivity(Intent(baseContext, Login::class.java))
            finish()
        }, 2000)

    }
}
