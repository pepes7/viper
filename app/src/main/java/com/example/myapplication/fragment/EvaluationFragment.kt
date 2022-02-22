package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.RegisterEvaluationActivity
import com.example.myapplication.adapter.EvaluationAdapter
import com.example.myapplication.adapter.QuestionRegisterAdapter
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.Question
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_evaluation.view.*
import java.util.*

class EvaluationFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var evaluationAdapter: EvaluationAdapter
    private var evaluations = arrayListOf<Evaluation>()
    private lateinit var evaluationsDatabase: DatabaseReference

    var key = ""
    fun initial(view: View) {
        recyclerView = view.findViewById(R.id.recycler_prof_evaluations)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.hasFixedSize()

        evaluationAdapter = EvaluationAdapter(view.context, evaluations)

        recyclerView.adapter = evaluationAdapter

        var pref = view.context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        key = pref.getString("key", "").toString()

        evaluationsDatabase =   FirebaseDatabase.getInstance().reference.child("Evaluations").child(key!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_evaluation, container, false)

        initial(view)
        view.btn_add_evaluation.setOnClickListener() {
            startActivity(Intent(context, RegisterEvaluationActivity::class.java))
        }

        getEvaluations()
        return view
    }

    fun getEvaluations() {
        evaluationsDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                evaluations.clear()
                for (d in dataSnapshot.children) {
                    val u = d.getValue(Evaluation::class.java)
                    var a : Evaluation = u!!
                    a.key = d.key+""
                    evaluations.add(a)
                }
                Collections.reverse(evaluations)
                evaluationAdapter.notifyDataSetChanged()
            }

        })

    }

}