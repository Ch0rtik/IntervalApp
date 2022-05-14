package com.kosmokamikaze.intervalapp.question

interface Question {
    fun ask()
    val rightButton: Int
    val subject: Int
    val subjectText: String
    var buttonTexts: List<String>
    val optionText: String
}