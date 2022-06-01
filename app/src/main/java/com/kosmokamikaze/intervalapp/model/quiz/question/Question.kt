package com.kosmokamikaze.intervalapp.model.quiz.question

interface Question {
    fun ask()
    val correctButtonNumbers: Set<Int>
    val subject: Int
    val subjectText: String
    var buttonTexts: List<String>
    val optionText: String
}