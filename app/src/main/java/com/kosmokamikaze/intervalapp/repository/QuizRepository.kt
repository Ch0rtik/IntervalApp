package com.kosmokamikaze.intervalapp.repository

import android.content.Context
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups

class QuizRepository(context: Context) {
    // private val quizDao = QuizDatabase.getDatabase(context).quizDao()

    private val ktorClient = KtorClient.getKtorClient(context)

    // fun readAllData(): LiveData<List<QuizData>> = quizDao.readAllData()

    suspend fun readQuizGroup(typeGroup: TypeGroups): List<QuizData> {
        return when (typeGroup) {
            TypeGroups.INTERVAL_GROUP -> ktorClient.getIntervalQuizData()
            TypeGroups.CHORD_GROUP -> ktorClient.getChordQuizData()
            TypeGroups.SCALE_GROUP -> ktorClient.getScaleQuizData()
        }
    }

    suspend fun updateHighScore(id: Int, highScore: Int) {
        ktorClient.updateHighScore(id, highScore)
    }

    suspend fun resetHighScores() {
        // quizDao.resetHighScore()
    }
}