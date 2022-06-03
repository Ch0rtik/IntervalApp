package com.kosmokamikaze.intervalapp.model.quiz

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kosmokamikaze.intervalapp.model.quiz.QuizTypes
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "quiz_data")
data class QuizData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var highScore: Int,
    val type: QuizTypes,
    val option: Int,
    val range: Int,
    val amountOfButtons: Int
) : Parcelable {
    constructor(type: QuizTypes, option: Int, range: Int, amountOfButtons: Int): this(0, 0, type, option, range, amountOfButtons)

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val HIGH_SCORE = "highScore"
        const val TYPE = "type"
        const val OPTION = "option"
        const val RANGE = "range"
        const val AMOUNT_OF_BUTTONS = "amountOfButtons"
        const val TYPE_GROUP = "typeGroup"
    }
}
