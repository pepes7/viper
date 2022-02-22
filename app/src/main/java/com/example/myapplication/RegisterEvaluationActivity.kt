package com.example.myapplication

import android.app.ProgressDialog
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.QuestionRegisterAdapter


import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.myapplication.model.*

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterEvaluationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionRegisterAdapter: QuestionRegisterAdapter
    private var questions = arrayListOf<Question>()
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var database: DatabaseReference

    var course: String = "Aux. Administrativo"
    var days: String = "Segunda"
    var shift: String = "Matutino"

    private lateinit var evaluation : Evaluation


    fun initial() {
        title = findViewById(R.id.txt_title_eval)
        desc = findViewById(R.id.txt_desc_eval)
        database = FirebaseDatabase.getInstance().reference
        evaluation = Evaluation()

        val spinnerCourses: Spinner = findViewById(R.id.spinner_courses_eval)
        val spinnerDays: Spinner = findViewById(R.id.spinner_days_eval)
        val spinnerShift: Spinner = findViewById(R.id.spinner_shift_eval)



        ArrayAdapter.createFromResource(
            this, R.array.courses, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCourses.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            this, R.array.days, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDays.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this, R.array.shift, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerShift.adapter = adapter
        }


        spinnerCourses.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                course = spinnerCourses.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })


        spinnerDays.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                days = spinnerDays.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })

        spinnerShift.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                shift = spinnerShift.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_evaluation)

        supportActionBar!!.title = "Cadastrar Avaliação"

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initial()

        recyclerView = findViewById(R.id.recycler_view_evaluation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        questionRegisterAdapter = QuestionRegisterAdapter(this, questions)

        recyclerView.adapter = questionRegisterAdapter
    }

    fun addQuestion(view: View) {
        questions.add(Question())
        questionRegisterAdapter.notifyDataSetChanged()

    }

    fun register (){
        var titleString = title.text.toString()
        var descString = desc.text.toString()

        evaluation.title = titleString
        evaluation.description = descString


        var pref = getSharedPreferences("user", MODE_PRIVATE)
        var key = pref.getString("key","")

        var codeClass = searchCode()
        val ref = database.child("Evaluations").child(key!!)
        val eval = ref.push().key
        ref.child(eval!!).child("title").setValue(titleString)
        ref.child(eval!!).child("description").setValue(descString)
        ref.child(eval!!).child("codeClass").setValue(codeClass)
        ref.child(eval!!).child("questions").setValue(questions)

        val pd = ProgressDialog(this)

        pd.setMessage("Cadastrando...")
        pd.show()
        finish()
    }

    fun searchCode() : String{
        var value = ""

        if (course.equals("Aux. Administrativo")) {
            value =  Courses.ADMIN.value
        } else if (course.equals("Aux. de Logística")) {
            value =  Courses.LOGIST.value
        } else if (course.equals("Aux. de Produção")) {
            value =  Courses.PROD.value
        } else if (course.equals("Aux. de Repositor Mercadorias")) {
            value =  Courses.REPOSIT.value
        } else if (course.equals("Aux. de Vendas")) {
            value =  Courses.VENDAS.value
        } else if (course.equals("Conservação e Limpeza")) {
            value =  Courses.LIMPEZA.value
        } else if (course.equals("Mecânica de Motos")) {
            value =  Courses.MOTOS.value
        } else if (course.equals("Mecânica em Manutenção")) {
            value =  Courses.MANUTENCAO.value
        } else if (course.equals("Mecânica de Veicular")) {
            value =  Courses.VEICUALR.value
        }

        if (days.equals("Segunda")) {
            value = value + Days.SEG.value
        } else if (days.equals("Terça")) {
            value = value + Days.TERC.value
        } else if (days.equals("Quarta")) {
            value = value + Days.QUAR.value
        } else if (days.equals("Quinta")) {
            value = value + Days.QUIN.value
        } else if (days.equals("Sexta")) {
            value = value + Days.SEX.value
        } else if (days.equals("Semanal")) {
            value = value + Days.SEMANAL.value
        }

        if (shift.equals("Matutino")) {
            value = value + Shift.MAT.value
        } else {
            value = value + Shift.VESP.value
        }


        return value
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.item_add_evaluation) {
            register()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_evaluation, menu)

        return true
    }
}
