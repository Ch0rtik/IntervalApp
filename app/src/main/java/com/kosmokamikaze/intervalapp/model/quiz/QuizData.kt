package com.kosmokamikaze.intervalapp.model.quiz


import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class QuizData (
    val id: Int,
    var highScore: Int,
    val type: QuizTypes,
    val option: Int,
    val range: Int,
    val amountOfButtons: Int
) {
    constructor(type: QuizTypes, option: Int, range: Int, amountOfButtons: Int): this(0, 0, type, option, range, amountOfButtons)

    companion object {
        const val ID = "id"
        const val HIGH_SCORE = "highScore"
        const val TYPE_GROUP = "typeGroup"
    }
}

@Serializable
data class QuizDataRequest (
    val id: Int
)

@Serializable
data class HighScoreRequest (
    val id: Int,
    val highScore: Int
)

@Serializable
data class QuizDataResponse(
    val id: Int,
    val highScore: Int,
    val type: String,
    val option: Int,
    val range: Int,
    val amountOfButtons: Int
)
