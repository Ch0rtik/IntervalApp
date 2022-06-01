package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kosmokamikaze.intervalapp.model.data.QuizDatabase
import com.kosmokamikaze.intervalapp.model.quiz.QuizData

class QuizRepository(context: Context) {
    private val quizDao = QuizDatabase.getDatabase(context).quizDao()

    fun readAllData(): LiveData<List<QuizData>> = quizDao.readAllData()

    fun readData(typeGroup: Int): LiveData<List<QuizData>> {
        return when (typeGroup) {
            0 -> quizDao.readIntervalQuizData()
            1 -> quizDao.readChordQuizData()
            2 -> quizDao.readScaleQuizData()
            else -> quizDao.readIntervalQuizData()
        }
    }

    suspend fun addQuiz(quizData: QuizData) {
        quizDao.addQuiz(quizData)
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        quizDao.updateHighScore(id, highScore)
    }
}