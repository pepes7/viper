package com.example.myapplication

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.sapereaude.maskedEditText.MaskedEditText
import com.example.myapplication.adapter.ClassAdapter
import com.example.myapplication.model.Courses
import com.example.myapplication.model.Days
import com.example.myapplication.model.Shift
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kofigyan.stateprogressbar.StateProgressBar

class RegisterActivity : AppCompatActivity() {
    private lateinit var stateProgressBar: StateProgressBar
    private lateinit var btnNext: Button
    private lateinit var btnBack: Button
    private lateinit var btnBackProf: Button
    private lateinit var btnRegister: Button
    private lateinit var btnRegisterProf: Button
    private lateinit var linearOne: LinearLayout
    private lateinit var linearTwo: LinearLayout
    private lateinit var linearThree: LinearLayout
    private lateinit var checkBox: CheckBox
    private lateinit var name: EditText
    private lateinit var cpf: MaskedEditText
    private lateinit var password: EditText
    private lateinit var matricula: EditText
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var classAdapter: ClassAdapter

    private var servicos = arrayListOf<String>()
    var codeClassProf = arrayListOf<String>()


    var course: String = "Aux. Administrativo"
    var days: String = "Segunda"
    var shift: String = "Matutino"
    var key: String= ""
    var codeClass: String = ""

    var courseProf: String = "Aux. Administrativo"
    var daysProf: String = "Segunda"
    var shiftProf: String = "Matutino"
    private lateinit var btnRegisterData: Button


    fun initial() {
        database = FirebaseDatabase.getInstance().reference
        val spinnerCourses: Spinner = findViewById(R.id.spinner_courses)
        val spinnerDays: Spinner = findViewById(R.id.spinner_days)
        val spinnerShift: Spinner = findViewById(R.id.spinner_shift)


        stateProgressBar = findViewById(R.id.progress)
        btnNext = findViewById(R.id.button_next)
        btnBack = findViewById(R.id.button_back)
        btnBackProf = findViewById(R.id.button_back_prof)
        linearOne = findViewById(R.id.linear1)
        linearTwo = findViewById(R.id.linear2)
        linearThree = findViewById(R.id.linear3)
        btnRegister = findViewById(R.id.button_register)
        btnRegisterProf = findViewById(R.id.button_register_prof)
        name = findViewById(R.id.editTextName)
        cpf = findViewById(R.id.editTextCpf)
        password = findViewById(R.id.editTextPassword)
        matricula = findViewById(R.id.editTextMatricula)
        checkBox = findViewById(R.id.checkbox)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        classAdapter = ClassAdapter(this, servicos, codeClassProf)

        recyclerView.adapter = classAdapter

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
        setContentView(R.layout.activity_register)

        initial()
        verifyStage()

    }


    fun verifyStage() {
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)

        if (stateProgressBar != null) {

            btnNext.setOnClickListener() {

                if (confirm(1)) {
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
                    linearOne.visibility = View.GONE

                    if (checkBox.isChecked) {
                        linearThree.visibility = View.VISIBLE
                    } else {
                        linearTwo.visibility = View.VISIBLE
                    }
                }
            }

            btnBack.setOnClickListener() {

                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)

                linearOne.visibility = View.VISIBLE
                linearTwo.visibility = View.GONE

            }
            btnBackProf.setOnClickListener() {

                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)

                linearOne.visibility = View.VISIBLE
                linearThree.visibility = View.GONE

            }
            btnRegister.setOnClickListener() {
                if (confirm(2)) {
                    var pref = getSharedPreferences("user", MODE_PRIVATE)
                    var editor = pref.edit()

                    editor.putString("cpf", this.cpf.text.toString())
                    editor.putString("name", this.name.text.toString())
                    editor.putString("password", this.password.text.toString())
                    editor.putBoolean("login", true)
                    editor.putString("prof", "false")
                    editor.putString("key", key)
                    editor.putString("codeClass", codeClass)

                    editor.commit()

                    startActivity(Intent(applicationContext, StudentHomeActivity::class.java))
                    Toast.makeText(
                        this,
                        "Usuário cadastrado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()

                }
            }

            btnRegisterProf.setOnClickListener() {
                if (confirm(3)) {

                    var pref = getSharedPreferences("user", MODE_PRIVATE)
                    var editor = pref.edit()
                    editor.putString("cpf", this.cpf.text.toString())
                    editor.putString("name", this.name.text.toString())
                    editor.putString("password", this.password.text.toString())
                    editor.putBoolean("login", true)
                    editor.putString("prof", "true")
                    editor.putString("key", key)

                    editor.commit()

                    startActivity(Intent(applicationContext, ProfHomeActivity::class.java))
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG)
                        .show()
                    finish()

                }
            }

        }


    }

    //verifica campos conforme a etapa
    fun confirm(etapa: Int): Boolean {
        var name = name.text.toString()
        var cpf = cpf.text.toString()
        var password = password.text.toString()

        if (etapa == 1) {

            if (name.isEmpty()) {
                this.name.error = "Campo obrigatório!"
                return false
            }

            if (cpf.isEmpty()) {
                this.cpf.error = "Campo obrigatório!"
                return false
            }
            if (password.isEmpty()) {
                this.password.error = "Campo obrigatório!"
                return false
            }


        } else if (etapa == 2) {
            var matricula = matricula.text.toString()
            if (matricula.isEmpty()) {
                this.matricula.error = "Campo obrigatório!"
                return false
            }
            val pd = ProgressDialog(this)

            pd.setMessage("Cadastrando...")
            pd.show()

            codeClass = searchCode(course, days, shift)

            val ref = database.child("usuarios")
            val user = ref.push().key
            key= user!!
            ref.child(user!!).child("name").setValue(name)
            ref.child(user!!).child("cpf").setValue(cpf)
            ref.child(user!!).child("password").setValue(password)
            ref.child(user!!).child("matricula").setValue(matricula)
            ref.child(user!!).child("course").setValue(course)
            ref.child(user!!).child("day").setValue(days)
            ref.child(user!!).child("shift").setValue(shift)
            ref.child(user!!).child("codeClass").setValue(codeClass)
            ref.child(user!!).child("prof").setValue("false")

        } else if (etapa == 3) {
            if (servicos.isEmpty()) {
                Toast.makeText(this, "Cadastre pelo menos uma turma!", Toast.LENGTH_LONG)
                    .show()
                return false;
            } else {
                val pd = ProgressDialog(this)

                pd.setMessage("Cadastrando...")
                pd.show()

                val ref = database.child("usuarios")
                val user = ref.push().key
                key = user!!
                ref.child(user!!).child("name").setValue(name)
                ref.child(user!!).child("cpf").setValue(cpf)
                ref.child(user!!).child("password").setValue(password)
                ref.child(user!!).child("classes").setValue(codeClassProf)
                ref.child(user!!).child("prof").setValue("true")
            }

        }

        return true
    }

    fun searchCode(course : String, days: String, shift: String) : String{
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

    fun cadClass(view: View) {
        var dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)


        dialog.setContentView(R.layout.custom_dialog)
        dialog.show()

        val spinnerCoursesDialog: Spinner = dialog.findViewById(R.id.spinner_courses_dialog)
        val spinnerDaysDialog: Spinner = dialog.findViewById(R.id.spinner_days_dialog)
        val spinnerShiftDialog: Spinner = dialog.findViewById(R.id.spinner_shift_dialog)
        btnRegisterData = dialog.findViewById(R.id.button_register_datas)

        ArrayAdapter.createFromResource(
            this, R.array.courses, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCoursesDialog.adapter = adapter

        }


        ArrayAdapter.createFromResource(
            this, R.array.days, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDaysDialog.adapter = adapter

        }

        ArrayAdapter.createFromResource(
            this, R.array.shift, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerShiftDialog.adapter = adapter
        }


        spinnerCoursesDialog.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                courseProf = spinnerCoursesDialog.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })


        spinnerDaysDialog.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                daysProf = spinnerDaysDialog.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })

        spinnerShiftDialog.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //(parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#bdbdbd"))
                (parent.getChildAt(0) as TextView).setTypeface(Typeface.DEFAULT)
                shiftProf = spinnerShiftDialog.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        })

        btnRegisterData.setOnClickListener() {
            changeRecycler(dialog)
        }
    }


    fun changeRecycler(dialog: Dialog) {
        var value = ""

        if (courseProf.equals("Aux. Administrativo")) {
            value = "Aux. Admin - "
        } else if (courseProf.equals("Aux. de Logística")) {
            value = "Aux. de Logis - "
        } else if (courseProf.equals("Aux. de Produção")) {
            value = "Aux. de Prod - "
        } else if (courseProf.equals("Aux. de Repositor Mercadorias")) {
            value = "Aux. de Rep. Merc - "
        } else if (courseProf.equals("Aux. de Vendas")) {
            value = "Aux. de Vendas - "
        } else if (courseProf.equals("Conservação e Limpeza")) {
            value = "Conser. e Limpeza - "
        } else if (courseProf.equals("Mecânica de Motos")) {
            value = "Mec. de Motos - "
        } else if (courseProf.equals("Mecânica em Manutenção")) {
            value = "Mec. em Manutenção - "
        } else if (courseProf.equals("Mecânica de Veicular")) {
            value = "Mec. de Veicular - "
        }


        if (shiftProf.equals("Matutino")) {
            value = value + "mat"
        } else {
            value = value + "vesp"
        }

        if (daysProf.equals("Segunda")) {
            value = value + " - seg"
        } else if (daysProf.equals("Terça")) {
            value = value + " - ter"
        } else if (daysProf.equals("Quarta")) {
            value = value + " - qua"
        } else if (daysProf.equals("Quinta")) {
            value = value + " - qui"
        } else if (daysProf.equals("Sexta")) {
            value = value + " - Sex"
        } else if (daysProf.equals("Semanal")) {
            value = value + " - semanal"
        }

        var code = searchCode(courseProf,daysProf, shiftProf)
        servicos.add(value)
        codeClassProf.add(code)
        classAdapter.notifyDataSetChanged()
        dialog.dismiss()
    }

    fun screenLogin(view: View) {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }


}