package com.kosmokamikaze.intervalapp.data

import android.util.Log
import androidx.lifecycle.LiveData

class QuizRepository(private val quizDao: QuizDao) {

    fun readAllData(): LiveData<List<QuizDataModel>> = quizDao.readAllData()

    suspend fun addQuiz(quiz: QuizDataModel) {
        quizDao.addQuiz(quiz)
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        quizDao.updateHighScore(id, highScore)
    }
}