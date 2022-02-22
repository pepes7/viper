package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Question
import com.example.myapplication.model.Response


class CorrectAdapter(
    private val context: Context,
    private val listQuestion: ArrayList<Question>,
    private val listResponse: ArrayList<Response>
) : RecyclerView.Adapter<CorrectAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.lis_correct_question, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listResponse.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val question = listQuestion.get(i)
        val response = listResponse.get(i)

        myViewHolder.title.text = question.title

        if (question.type.equals("Descritiva")) {
            myViewHolder.linear.visibility = View.GONE
            myViewHolder.response.visibility = View.VISIBLE

            myViewHolder.response.text = response.response

        } else {
            myViewHolder.linear.visibility = View.VISIBLE
            myViewHolder.response.visibility = View.GONE
            initText(myViewHolder, question)
            controlRadio(myViewHolder, response)
        }


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var linear: LinearLayout
        var response: TextView
        var questionA: TextView
        var questionB: TextView
        var questionC: TextView
        var questionD: TextView
        var questionE: TextView

        var radioA: RadioButton
        var radioB: RadioButton
        var radioC: RadioButton
        var radioD: RadioButton
        var radioE: RadioButton

        var linearA: LinearLayout
        var linearB: LinearLayout
        var linearC: LinearLayout
        var linearD: LinearLayout
        var linearE: LinearLayout
        init {
            title = itemView.findViewById(R.id.title_question_answer)
            linear = itemView.findViewById(R.id.linear_option_answer)
            response = itemView.findViewById(R.id.edit_response)
            questionA = itemView.findViewById(R.id.txt_question_a)
            questionB = itemView.findViewById(R.id.txt_question_b)
            questionC = itemView.findViewById(R.id.txt_question_c)
            questionD = itemView.findViewById(R.id.txt_question_d)
            questionE = itemView.findViewById(R.id.txt_question_e)
            radioA = itemView.findViewById(R.id.rb_a)
            radioB = itemView.findViewById(R.id.rb_b)
            radioC = itemView.findViewById(R.id.rb_c)
            radioD = itemView.findViewById(R.id.rb_d)
            radioE = itemView.findViewById(R.id.rb_e)

            linearA = itemView.findViewById(R.id.linear_a)
            linearB = itemView.findViewById(R.id.linear_b)
            linearC = itemView.findViewById(R.id.linear_c)
            linearD = itemView.findViewById(R.id.linear_d)
            linearE = itemView.findViewById(R.id.linear_e)
        }

    }

    fun initText(myView: MyViewHolder, question: Question, ) {
        myView.questionA.text = question.a
        myView.questionB.text = question.b
        myView.questionC.text = question.c
        myView.questionD.text = question.d
        myView.questionE.text = question.e


        if (question.a.equals("")) {
            myView.linearA.visibility = View.GONE
        }
        if (question.b.equals("")) {
            myView.linearB.visibility = View.GONE
        }
        if (question.c.equals("")) {
            myView.linearC.visibility = View.GONE
        }
        if (question.d.equals("")) {
            myView.linearD.visibility = View.GONE
        }
        if (question.e.equals("")) {
            myView.linearE.visibility = View.GONE
        }
    }

    fun controlRadio(myViewHolder: MyViewHolder, response: Response) {
        if (response.alternative.equals("a")) {
            myViewHolder.radioA.isChecked = true
        } else if (response.alternative.equals("b")) {
            myViewHolder.radioB.isChecked = true
        } else if (response.alternative.equals("c")) {
            myViewHolder.radioC.isChecked = true
        } else if (response.alternative.equals("d")) {
            myViewHolder.radioD.isChecked = true
        } else if (response.alternative.equals("e")) {
            myViewHolder.radioE.isChecked = true
        }
    }
}