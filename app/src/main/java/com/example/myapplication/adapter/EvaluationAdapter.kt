package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ListStudentsResponseActivity
import com.example.myapplication.R
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.currentEvaluation


class EvaluationAdapter(
    private val context: Context,
    private val listQuestion: ArrayList<Evaluation>,
) : RecyclerView.Adapter<EvaluationAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_evaluations, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listQuestion.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val question = listQuestion.get(i)

        myViewHolder.title.text = question.title

        myViewHolder.itemView.setOnClickListener{
            val intent = Intent(context,ListStudentsResponseActivity::class.java)
            currentEvaluation = question
            context.startActivity(intent)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(R.id.txt_title_evaluation)
        }
    }


}