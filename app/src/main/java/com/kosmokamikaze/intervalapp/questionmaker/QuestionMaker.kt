package com.kosmokamikaze.intervalapp.questionmaker

interface QuestionMaker {
    fun feedArguments(prevSubj: Int, rightButton: Int)
    fun getSubjectText(): String
    fun getButtonTexts(): List<String>
    fun getOptionText(): String
    fun getSubject(): Int
    fun getAmountOfAnswers(): Int
}