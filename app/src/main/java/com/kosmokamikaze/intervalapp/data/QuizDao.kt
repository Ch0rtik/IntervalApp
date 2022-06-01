package com.kosmokamikaze.intervalapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kosmokamikaze.intervalapp.model.QuizDataModel

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuiz(quiz: QuizDataModel)

    @Query("SELECT * FROM quiz_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<QuizDataModel>>

    @Query("SELECT * FROM quiz_data WHERE (type == 'NOTE_FROM_INTERVAL' OR type == 'INTERVAL_FROM_NOTES' ) ORDER BY id ASC")
    fun readIntervalQuizData(): LiveData<List<QuizDataModel>>

    @Query("SELECT * FROM quiz_data WHERE (type == 'CHORD_FROM_NOTES' OR type == 'NOTES_FROM_CHORD') ORDER BY id ASC")
    fun readChordQuizData(): LiveData<List<QuizDataModel>>

    @Query("SELECT * FROM quiz_data WHERE type == 'NOTES_FROM_SCALE' ORDER BY id ASC")
    fun readScaleQuizData(): LiveData<List<QuizDataModel>>

    @Query("UPDATE quiz_data SET highScore = :newHighScore WHERE id = :id")
    suspend fun updateHighScore(id: Int, newHighScore: Int)
}