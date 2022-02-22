package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.CorrectAdapter
import com.example.myapplication.adapter.EvaluationAnswerAdapter
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.Response
import com.example.myapplication.model.ResponseEvaluation
import com.example.myapplication.model.currentEvaluation
import com.google.firebase.database.*
import java.util.*

class CorrectEvaluationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var student: TextView
    private lateinit var correctAdapter: CorrectAdapter
    private lateinit var database: DatabaseReference
    private lateinit var data: Bundle

    var response = arrayListOf<Response>()

    var key = ""
    fun initial() {
        title = findViewById(R.id.txt_title_eval_correct)
        description = findViewById(R.id.txt_desc_eval_correct)
        student = findViewById(R.id.txt_aluno)
        database = FirebaseDatabase.getInstance().reference
        data = intent.extras!!

        student.text = "Alunoª: " + data.getString("name")
        title.text = currentEvaluation.title
        description.text = currentEvaluation.description

        recyclerView = findViewById(R.id.recycler_view_evaluation_student)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        correctAdapter = CorrectAdapter(this, currentEvaluation.questions,response)

        recyclerView.adapter = correctAdapter


        var pref = getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        key = pref.getString("key", "").toString()
        val keyStudent = data.getString("key")
        database =
            FirebaseDatabase.getInstance().reference.child("Evaluations").child(key!!).child(
                currentEvaluation.key
            ).child("response").child(keyStudent!!).child("questions")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correct_evaluation)
        supportActionBar!!.title = "Correção de avaliação"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initial()
        getEvaluations()
    }

    fun getEvaluations() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                response.clear()
                for (d in dataSnapshot.children) {
                    val u = d.getValue(Response::class.java)
                    response.add(u!!)
                }
                correctAdapter.notifyDataSetChanged()
            }

        })

    }
}