package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para o Splash ocupar toda a tela do celular
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var pref = getSharedPreferences("user", MODE_PRIVATE)
        var login = pref.getBoolean("login",false)
        var isProf = pref.getString("prof","")


        Handler().postDelayed({
            if(login){
                if(isProf.equals("true")){
                    startActivity(Intent(applicationContext, ProfHomeActivity::class.java))
                }else{
                    startActivity(Intent(applicationContext, StudentHomeActivity::class.java))
                }

            }else{
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }

            finish()
        }, 2000)

    }
}