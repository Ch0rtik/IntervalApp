package com.kosmokamikaze.intervalapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kosmokamikaze.intervalapp.quiz.QuizTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "quiz_data")
data class QuizDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val highScore: Int,
    val type: QuizTypes,
    val option: Int,
    val range: Int,
    val amountOfButtons: Int): Parcelable {

    constructor(title: String, type: QuizTypes, option: Int, range: Int, amountOfButtons: Int): this(0, title, 0, type, option, range, amountOfButtons)

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
