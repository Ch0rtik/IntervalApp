package com.kosmokamikaze.intervalapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuiz(quiz: QuizDataModel)


    @Query("SELECT * FROM quiz_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<QuizDataModel>>
}