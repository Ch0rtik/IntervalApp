package com.kosmokamikaze.intervalapp.questionmaker

interface QuestionMaker {
    fun getRightAnswer(subj: Int): Int
    fun getSubjectText(subj: Int): String
    fun getButtonText(id: Int): String
    fun getOptionText(): String
}