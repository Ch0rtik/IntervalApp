package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kosmokamikaze.intervalapp.model.data.QuizDatabase
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups

class QuizRepository(context: Context) {
    private val quizDao = QuizDatabase.getDatabase(context).quizDao()

    fun readAllData(): LiveData<List<QuizData>> = quizDao.readAllData()

    fun readQuizGroup(typeGroup: TypeGroups): LiveData<List<QuizData>> {
        return when (typeGroup) {
            TypeGroups.INTERVAL_GROUP -> quizDao.readIntervalQuizData()
            TypeGroups.CHORD_GROUP -> quizDao.readChordQuizData()
            TypeGroups.SCALE_GROUP -> quizDao.readScaleQuizData()
        }
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        quizDao.updateHighScore(id, highScore)
    }

    suspend fun resetHighScores() {
        quizDao.resetHighScore()
    }
}