package com.kosmokamikaze.intervalapp.models

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
    val amountOfButtons: Int) {

    constructor(title: String, type: Int, option: Int, range: Int, amountOfButtons: Int): this(0, title, 0, type, option, range, amountOfButtons)

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val HIGH_SCORE = "highScore"
        const val TYPE = "type"
        const val OPTION = "option"
        const val RANGE = "range"
        const val AMOUNT_OF_BUTTONS = "amountOfButtons"
    }
}
