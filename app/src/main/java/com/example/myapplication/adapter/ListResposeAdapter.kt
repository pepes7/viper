package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CorrectEvaluationActivity
import com.example.myapplication.EvaluationActivity
import com.example.myapplication.R
import com.example.myapplication.model.ResponseEvaluation
import com.example.myapplication.model.currentEvaluation


class ListResposeAdapter(
    private val context: Context,
    private val listQuestion: ArrayList<ResponseEvaluation>,
) : RecyclerView.Adapter<ListResposeAdapter.MyViewHolder>() {

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

        myViewHolder.title.text = question.name

        myViewHolder.itemView.setOnClickListener {
            val intent = Intent(context, CorrectEvaluationActivity::class.java)

            intent.putExtra("name", question.name)
            intent.putExtra("key", question.key)

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