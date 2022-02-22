package com.example.myapplication.model

open class Usuario(
    open var cpf: String = "",
    open var name: String = "",
    open var course: String = "",
    open var day: String = "",
    open var codeClass: String = "",
    open var matricula: String = "",
    open var password: String = "",
    open var shift: String = "",
    open var prof: String = "",
)


enum class Courses(val value: String) {
    ADMIN("1"),
    LOGIST("2"),
    PROD("3"),
    REPOSIT("4"),
    VENDAS("5"),
    LIMPEZA("6"),
    MOTOS("7"),
    MANUTENCAO("8"),
    VEICUALR("9"),
}

enum class Days(val value: String) {
    SEG("2"),
    TERC("3"),
    QUAR("4"),
    QUIN("5"),
    SEX("6"),
    SEMANAL("7"),

}

enum class Shift(val value: String) {
    MAT("1"),
    VESP("2"),
}

open class Evaluation(
    open var key: String = "",
    open var title: String = "",
    open var description: String = "",
    open var codeClass: String = "",
    open var questions:  ArrayList<Question> = arrayListOf<Question>()

    )

open class Question(
    open var title: String = "",
    open var type: String = "",
    open var a: String = "",
    open var b: String = "",
    open var c: String = "",
    open var d: String = "",
    open var e: String = "",
    open var correct: String = ""

)

open class ResponseEvaluation(
    open var key: String = "",
    open var name: String = "",
    open var questions:  ArrayList<Response> = arrayListOf<Response>()

)

open class Response(
    open var response: String = "",
    open var alternative: String = "",

    )

var currentEvaluation = Evaluation()