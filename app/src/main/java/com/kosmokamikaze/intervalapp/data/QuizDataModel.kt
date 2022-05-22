package com.kosmokamikaze.intervalapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_data")
data class QuizDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val highScore: Int,
    val type: Int,
    val option: Int,
    val range: Int,
    val amountOfButtons: Int)
