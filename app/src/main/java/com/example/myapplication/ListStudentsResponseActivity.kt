package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.EvaluationStudentAdapter
import com.example.myapplication.adapter.ListResposeAdapter
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.ResponseEvaluation
import com.example.myapplication.model.currentEvaluation
import com.google.firebase.database.*
import java.util.*

class ListStudentsResponseActivity : AppCompatActivity() {
    private lateinit var data : Bundle
    private lateinit var recyclerView: RecyclerView
    private lateinit var evaluationAdapter: ListResposeAdapter
    private var responseEvaluation = arrayListOf<ResponseEvaluation>()
    private lateinit var responseDatabase: DatabaseReference

    var key = ""
    fun initial(){
        recyclerView = findViewById(R.id.recycler)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        evaluationAdapter = ListResposeAdapter(this, responseEvaluation)

        recyclerView.adapter = evaluationAdapter


        var pref = getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        key = pref.getString("key", "").toString()
        responseDatabase =   FirebaseDatabase.getInstance().reference.child("Evaluations").child(key!!).child(
            currentEvaluation.key).child("response")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_students_response)

        supportActionBar!!.title = currentEvaluation.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initial()
        getEvaluations()
    }

    fun getEvaluations() {
        responseDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                responseEvaluation.clear()
                for (d in dataSnapshot.children) {
                    val u = d.getValue(ResponseEvaluation::class.java)
                    val a : ResponseEvaluation = u!!
                    a.key = d.key+""
                    responseEvaluation.add(a)
                }
                Collections.reverse(responseEvaluation)
                evaluationAdapter.notifyDataSetChanged()
            }

        })


    }
}