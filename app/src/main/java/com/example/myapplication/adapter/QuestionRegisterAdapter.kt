package com.example.myapplication.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Question
import android.widget.RadioButton


class QuestionRegisterAdapter(
    private val context: Context,
    private val listQuestion: ArrayList<Question>,
) : RecyclerView.Adapter<QuestionRegisterAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_resgister_question, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listQuestion.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val question = listQuestion.get(i)
        var string = ""

        //Toast.makeText(context,"sss", Toast.LENGTH_SHORT).show()

        myViewHolder.title.addTextChangedListener {
            question.title = myViewHolder.title.text.toString()
        }
        myViewHolder.questionA.addTextChangedListener {
            question.a = myViewHolder.questionA.text.toString()
        }
        myViewHolder.questionB.addTextChangedListener {
            question.b = myViewHolder.questionB.text.toString()
        }
        myViewHolder.questionC.addTextChangedListener {
            question.c = myViewHolder.questionC.text.toString()
        }
        myViewHolder.questionD.addTextChangedListener {
            question.d = myViewHolder.questionD.text.toString()
        }
        myViewHolder.questionE.addTextChangedListener {
            question.e = myViewHolder.questionE.text.toString()
        }

        myViewHolder.radioA.setOnClickListener() {
            myViewHolder.radioB.isChecked = false
            myViewHolder.radioC.isChecked = false
            myViewHolder.radioD.isChecked = false
            myViewHolder.radioE.isChecked = false
            question.correct = "a"

        }

        myViewHolder.radioB.setOnClickListener() {
            myViewHolder.radioA.isChecked = false
            myViewHolder.radioC.isChecked = false
            myViewHolder.radioD.isChecked = false
            myViewHolder.radioE.isChecked = false
            question.correct = "b"

        }
        myViewHolder.radioC.setOnClickListener() {
            myViewHolder.radioB.isChecked = false
            myViewHolder.radioA.isChecked = false
            myViewHolder.radioD.isChecked = false
            myViewHolder.radioE.isChecked = false
            question.correct = "c"

        }
        myViewHolder.radioD.setOnClickListener() {
            myViewHolder.radioB.isChecked = false
            myViewHolder.radioC.isChecked = false
            myViewHolder.radioA.isChecked = false
            myViewHolder.radioE.isChecked = false
            question.correct = "d"

        }
        myViewHolder.radioE.setOnClickListener() {
            myViewHolder.radioB.isChecked = false
            myViewHolder.radioC.isChecked = false
            myViewHolder.radioD.isChecked = false
            myViewHolder.radioA.isChecked = false
            question.correct = "e"
        }




        myViewHolder.spinnerQuestion.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                string = myViewHolder.spinnerQuestion.getItemAtPosition(position).toString()

                if (string.equals("Descritiva")) {
                    myViewHolder.linear.visibility = View.GONE
                    question.type = string
                } else {
                    myViewHolder.linear.visibility = View.VISIBLE
                    question.type = string
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var spinnerQuestion: Spinner
        var linear: LinearLayout
        var title: EditText
        var questionA: EditText
        var questionB: EditText
        var questionC: EditText
        var questionD: EditText
        var questionE: EditText

        var radioA: RadioButton
        var radioB: RadioButton
        var radioC: RadioButton
        var radioD: RadioButton
        var radioE: RadioButton

        var delete : ImageButton


        init {
            spinnerQuestion = itemView.findViewById(R.id.spinner_question)
            linear = itemView.findViewById(R.id.linear_option)
            title = itemView.findViewById(R.id.title_question)
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

            delete = itemView.findViewById(R.id.btn_delete_question)

            ArrayAdapter.createFromResource(
                context, R.array.type_question, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerQuestion.adapter = adapter
            }

        }
    }


}