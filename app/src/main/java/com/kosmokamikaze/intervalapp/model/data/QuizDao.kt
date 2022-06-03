package com.kosmokamikaze.intervalapp.model.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kosmokamikaze.intervalapp.model.quiz.QuizData

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuiz(quiz: QuizData)

    @Query("SELECT * FROM quiz_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<QuizData>>

    @Query("SELECT * FROM quiz_data WHERE (type == 'NOTE_FROM_INTERVAL' OR type == 'INTERVAL_FROM_NOTES' ) ORDER BY id ASC")
    fun readIntervalQuizData(): LiveData<List<QuizData>>

    @Query("SELECT * FROM quiz_data WHERE (type == 'CHORD_FROM_NOTES' OR type == 'NOTES_FROM_CHORD') ORDER BY id ASC")
    fun readChordQuizData(): LiveData<List<QuizData>>

    @Query("SELECT * FROM quiz_data WHERE type == 'NOTES_FROM_SCALE' ORDER BY id ASC")
    fun readScaleQuizData(): LiveData<List<QuizData>>

    @Query("UPDATE quiz_data SET highScore = :newHighScore WHERE id = :id")
    suspend fun updateHighScore(id: Int, newHighScore: Int)

    @Query("UPDATE quiz_data SET highScore = 0")
    suspend fun resetHighScore()
}