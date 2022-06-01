package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kosmokamikaze.intervalapp.data.QuizDao
import com.kosmokamikaze.intervalapp.data.QuizDatabase
import com.kosmokamikaze.intervalapp.models.QuizDataModel

class QuizRepository(context: Context) {
    private val quizDao = QuizDatabase.getDatabase(context).quizDao()

    fun readAllData(): LiveData<List<QuizDataModel>> = quizDao.readAllData()

    fun readData(typeGroup: Int): LiveData<List<QuizDataModel>> {
        return when(typeGroup) {
            0 -> quizDao.readIntervalQuizData()
            1 -> quizDao.readChordQuizData()
            2 -> quizDao.readScaleQuizData()
            else -> quizDao.readIntervalQuizData()
        }
    }

    suspend fun addQuiz(quiz: QuizDataModel) {
        quizDao.addQuiz(quiz)
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        quizDao.updateHighScore(id, highScore)
    }
}