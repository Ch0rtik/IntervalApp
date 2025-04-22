package com.kosmokamikaze.intervalapp.model.quiz

import kotlinx.serialization.Serializable

@Serializable
enum class QuizTypes {
    NOTE_FROM_INTERVAL, INTERVAL_FROM_NOTES, CHORD_FROM_NOTES, NOTES_FROM_CHORD, NOTES_FROM_SCALE
}