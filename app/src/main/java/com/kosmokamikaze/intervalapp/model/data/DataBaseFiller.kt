package com.kosmokamikaze.intervalapp.model.data

import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.QuizTypes
import com.kosmokamikaze.intervalapp.repository.QuizRepository

class DataBaseFiller {
    companion object {
        suspend fun addInitialValues(repository: QuizRepository) {
            repository.addQuiz(QuizData("ЧИСТЫЕ КВИНТЫ", QuizTypes.NOTE_FROM_INTERVAL, 1, 5, 4))
            repository.addQuiz(QuizData("ЧИСТЫЕ КВАРТЫ", QuizTypes.NOTE_FROM_INTERVAL, -1, 5, 4))
            repository.addQuiz(QuizData("БОЛЬШИЕ ТЕРЦИИ", QuizTypes.NOTE_FROM_INTERVAL, 4, 5, 4))
            repository.addQuiz(QuizData("МАЛЫЕ ТЕРЦИИ", QuizTypes.NOTE_FROM_INTERVAL, -3, 5, 4))
            repository.addQuiz(QuizData("БОЛЬШИЕ СЕПТИМЫ", QuizTypes.NOTE_FROM_INTERVAL, 5, 5, 4))
            repository.addQuiz(QuizData("МАЛЫЕ СЕПТИМЫ", QuizTypes.NOTE_FROM_INTERVAL, -2, 5, 4))
            repository.addQuiz(QuizData("БОЛЬШИЕ СЕКУНДЫ", QuizTypes.NOTE_FROM_INTERVAL, 2, 5, 4))
            repository.addQuiz(QuizData("МАЛЫЕ СЕКУНДЫ", QuizTypes.NOTE_FROM_INTERVAL, -5, 5, 4))
            repository.addQuiz(QuizData("БОЛЬШИЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, 3, 5, 4))
            repository.addQuiz(QuizData("МАЛЫЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, -4, 5, 4))
            repository.addQuiz(QuizData("БОЛЬШИЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, 3, 5, 4))
            repository.addQuiz(QuizData("МАЛЫЕ СЕКСТЫ", QuizTypes.NOTE_FROM_INTERVAL, -4, 5, 4))
            repository.addQuiz(QuizData("УМЕНЬШЕННЫЕ КВИНТЫ", QuizTypes.NOTE_FROM_INTERVAL, -6, 5, 4))
            repository.addQuiz(QuizData("УВЕЛИЧЕННЫЕ КВАРТЫ", QuizTypes.NOTE_FROM_INTERVAL, 6, 5, 4))

            repository.addQuiz(QuizData("ИНТЕРВАЛ ПО НОТАМ", QuizTypes.INTERVAL_FROM_NOTES, 0, 5, 4))

            repository.addQuiz(QuizData("МАЖОРНЫЕ ТРЕЗВУЧИЯ",  QuizTypes.CHORD_FROM_NOTES, 10, 5, 3))
            repository.addQuiz(QuizData("МИНОРНЫЕ ТРЕЗВУЧИЯ",  QuizTypes.CHORD_FROM_NOTES, 6, 5, 3))
            repository.addQuiz(QuizData("MAJ7 АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 42, 5, 4))
            repository.addQuiz(QuizData("MIN7 АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 25, 5, 4))
            repository.addQuiz(QuizData("ДОМИНАНТНЫЕ АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 41, 5, 4))
            repository.addQuiz(QuizData("MINMAJ АККОРДЫ",  QuizTypes.CHORD_FROM_NOTES, 26, 5, 4))

            repository.addQuiz(QuizData("ИОНИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 2726, 5, 9))
            repository.addQuiz(QuizData("ЭОЛИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 1637, 5, 9))
            repository.addQuiz(QuizData("ФРИГИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 1621, 5, 9))
            repository.addQuiz(QuizData("ДОРИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 1638, 5, 9))
            repository.addQuiz(QuizData("МИКСОЛИДИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 2662, 5, 9))
            repository.addQuiz(QuizData("ЛИДИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 2730, 5, 9))
            repository.addQuiz(QuizData("ЛОКРИЙСКИЙ ЛАД", QuizTypes.NOTES_FROM_SCALE, 1365, 5, 9))
        }
    }
}