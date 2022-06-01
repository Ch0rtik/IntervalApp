package com.kosmokamikaze.intervalapp.data

import com.kosmokamikaze.intervalapp.model.QuizDataModel
import com.kosmokamikaze.intervalapp.quiz.QuizTypes
import com.kosmokamikaze.intervalapp.repository.QuizRepository

class DataBaseFiller {
    companion object {
        suspend fun addInitialValues(repository: QuizRepository) {
            repository.addQuiz(QuizDataModel("ЧИСТЫЕ КВИНТЫ", QuizTypes.NOTE_FROM_INTERVAL, 1, 5, 4))
            repository.addQuiz(QuizDataModel("ЧИСТЫЕ КВАРТЫ", QuizTypes.NOTE_FROM_INTERVAL, -1, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ ТЕРЦИИ", QuizTypes.NOTE_FROM_INTERVAL, 4, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ ТЕРЦИИ", QuizTypes.NOTE_FROM_INTERVAL, -3, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕПТИМЫ", QuizTypes.NOTE_FROM_INTERVAL, 5, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕПТИМЫ", QuizTypes.NOTE_FROM_INTERVAL, -2, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКУНДЫ", QuizTypes.NOTE_FROM_INTERVAL, 2, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКУНДЫ", QuizTypes.NOTE_FROM_INTERVAL, -5, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, 3, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, -4, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, 3, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, -4, 5, 4))
            repository.addQuiz(QuizDataModel("УМЕНЬШЕННЫЕ КВИНТЫ", QuizTypes.NOTE_FROM_INTERVAL, -6, 5, 4))
            repository.addQuiz(QuizDataModel("УВЕЛИЧЕННЫЕ КВАРТЫ", QuizTypes.NOTE_FROM_INTERVAL, 6, 5, 4))

            repository.addQuiz(QuizDataModel("ИНТЕРВАЛ ПО НОТАМ", QuizTypes.INTERVAL_FROM_NOTES, 0, 5, 4))

            repository.addQuiz(QuizDataModel("МАЖОРНЫЕ ТРЕЗВУЧИЯ",  QuizTypes.CHORD_FROM_NOTES, 10, 5, 3))
            repository.addQuiz(QuizDataModel("МИНОРНЫЕ ТРЕЗВУЧИЯ",  QuizTypes.CHORD_FROM_NOTES, 6, 5, 3))
            repository.addQuiz(QuizDataModel("MAJ7 АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 42, 5, 3))
            repository.addQuiz(QuizDataModel("MIN7 АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 25, 5, 3))
            repository.addQuiz(QuizDataModel("ДОМИНАНТНЫЕ АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 41, 5, 3))
            repository.addQuiz(QuizDataModel("MINMAJ АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 26, 5, 3))

            repository.addQuiz(QuizDataModel(0,"ИОНИЙСКИЙ ЛАД", 0, QuizTypes.NOTES_FROM_SCALE, 2726, 5, 9))
        }
    }
}