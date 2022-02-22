package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.fragment.EvaluationStudentFragment


class StudentHomeActivity : AppCompatActivity() {
    private lateinit var name : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        name = findViewById(R.id.txt_name_user)
        var pref = getSharedPreferences("user", MODE_PRIVATE)
        var nameUser = pref.getString("name","")
        var isProf = pref.getString("prof","")


        if(isProf.equals("true")){
            name.text = "Olá, Prof. "+nameUser+"!"

        }else{
            name.text = "Olá, "+nameUser+"!"
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.viewPageStudent, EvaluationStudentFragment())
        transaction.commit()
    }

    fun logout(view: View){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Deseja realmente sair da sua conta?")
            .setPositiveButton("Sair",
                DialogInterface.OnClickListener { dialog, id ->
                    var pref = getSharedPreferences("user", MODE_PRIVATE)
                    var editor = pref.edit()
                    editor.clear()
                    editor.commit()
                    startActivity(
                        Intent(
                            applicationContext,
                            LoginActivity::class.java
                        )
                    )
                    finish()
                })
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                })
        // Create the AlertDialog object and return it
        val dialog = builder.create()
        dialog.show()
    }
}