package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.model.Usuario
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var cpf: EditText
    private lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = FirebaseDatabase.getInstance().reference.child("usuarios")
        cpf = findViewById(R.id.editCpfLogin)
        password = findViewById(R.id.editPasswordlogin)



    }

    fun screenRegister(view: View) {
        startActivity(Intent(applicationContext, RegisterActivity::class.java))

    }

    fun screenHome(view: View) {
        var cpf = cpf.text.toString()
        var password = password.text.toString()
        var login = true


        if (cpf.isEmpty()) {
            this.cpf.error = "Campo obrigatório!"
            login = false
        }
        if (password.isEmpty()) {
            this.password.error = "Campo obrigatório!"
            login = false
        }

        if (login) {
            login(cpf, password)

        }
    }

    fun login(cpf: String, password: String) {
        var encontrou = false;
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (d in dataSnapshot.children) {
                    val u = d.getValue(Usuario::class.java)

                    if (u != null) {
                        //Toast.makeText(applicationContext, "" + cpf, Toast.LENGTH_SHORT).show()
                        if (u.cpf.equals(cpf)) {
                            encontrou = true
                            if (u.password.equals(password)) {
                                var pref = getSharedPreferences("user", MODE_PRIVATE)
                                var editor = pref.edit()
                                editor.putString("cpf", cpf)
                                editor.putString("name", u.name)
                                editor.putString("password", password)
                                editor.putBoolean("login", true)
                                editor.putString("prof", u.prof)
                                editor.putString("key", d.key)


                                if(u.prof.equals("true")){
                                    editor.commit()
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            ProfHomeActivity::class.java
                                        )
                                    )
                                }else{
                                    editor.putString("codeClass", u.codeClass)
                                    editor.commit()
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            StudentHomeActivity::class.java
                                        )
                                    )
                                }

                                finish();
                            }else{
                                Toast.makeText(applicationContext, "Senha errada. Verifique e tente novamente" , Toast.LENGTH_SHORT).show()
                            }
                            break;
                        }
                    }
                }
                if(!encontrou){
                    Toast.makeText(applicationContext, "Usuário não encontrado" , Toast.LENGTH_SHORT).show()

                }
            }

        })
    }
}