package com.kosmokamikaze.intervalapp.data

import androidx.lifecycle.LiveData

class QuizRepository(private val quizDao: QuizDao) {

    val readAllData: LiveData<List<QuizDataModel>> = quizDao.readAllData()

    suspend fun addQuiz(quiz: QuizDataModel) {
        quizDao.addQuiz(quiz)
    }
}