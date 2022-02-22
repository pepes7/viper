package com.example.myapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.EvaluationAnswerAdapter
import com.example.myapplication.model.Response
import com.example.myapplication.model.currentEvaluation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EvaluationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var evaluationAdapter: EvaluationAnswerAdapter
    private lateinit var database: DatabaseReference


    var response = arrayListOf<Response>()

    fun initial() {
        title = findViewById(R.id.txt_title_eval_student)
        description = findViewById(R.id.txt_desc_eval_student)
        database = FirebaseDatabase.getInstance().reference

        title.text = currentEvaluation.title
        description.text = currentEvaluation.description

        recyclerView = findViewById(R.id.recycler_view_evaluation_student)

        response.clear()
        for (i in currentEvaluation.questions) {
            var a = Response()

            response.add(a)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        evaluationAdapter = EvaluationAnswerAdapter(this, currentEvaluation.questions, response)

        recyclerView.adapter = evaluationAdapter


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        supportActionBar!!.title = "Avaliação"

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initial()
    }

    fun register() {
        var pref = getSharedPreferences("user", MODE_PRIVATE)
        var key = pref.getString("key", "")
        var name = pref.getString("name", "")


        val ref = database.child("Evaluations").child(currentEvaluation.key).child("response")
            .child(key!!)

        ref.child("name").setValue(name)
        ref.child("questions").setValue(response)


        val pd = ProgressDialog(this)

        pd.setMessage("Enviando...")
        pd.show()
        Toast.makeText(this, "Avaliação entregue com sucesso", Toast.LENGTH_LONG).show()
        finish()

    }

    fun send() {
        val builder = AlertDialog.Builder(this,R.style.Theme_Design_BottomSheetDialog)
        builder.setTitle("Deseja realmente entregar sua avaliação?")
            .setMessage("Verifique se preencheu todas as questões")
            .setPositiveButton("Entregar",
                DialogInterface.OnClickListener { dialog, id ->
                    register()
                })
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                })
        // Create the AlertDialog object and return it
        val dialog = builder.create()
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.item_add_evaluation) {
            send()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_evaluation, menu)

        return true
    }
}