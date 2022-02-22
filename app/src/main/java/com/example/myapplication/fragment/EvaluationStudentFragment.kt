package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.EvaluationAdapter
import com.example.myapplication.adapter.EvaluationStudentAdapter
import com.example.myapplication.model.Evaluation
import com.google.firebase.database.*
import java.util.*


class EvaluationStudentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var evaluationAdapter: EvaluationStudentAdapter
    private var evaluations = arrayListOf<Evaluation>()
    private lateinit var evaluationsDatabase: DatabaseReference

    var codeClass = ""

    fun initial(view: View) {
        var pref = view.context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        codeClass = pref.getString("codeClass", "").toString()

        recyclerView = view.findViewById(R.id.recycler_student_evaluations)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.hasFixedSize()

        evaluationAdapter = EvaluationStudentAdapter(view.context, evaluations)

        recyclerView.adapter = evaluationAdapter



        evaluationsDatabase =
            FirebaseDatabase.getInstance().reference.child("Evaluations")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_evaluation_student, container, false)


        initial(view)

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
                    for (child in d.children) {
                        val u = child.getValue(Evaluation::class.java)
                        if(u!!.codeClass.equals(codeClass)){
                            var a : Evaluation = u!!
                            a.key = d.key +"/"+ child.key
                            evaluations.add(a)

                        }
                    }

                }
                Collections.reverse(evaluations)
                evaluationAdapter.notifyDataSetChanged()
            }

        })


    }
}