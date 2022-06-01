package com.kosmokamikaze.intervalapp.data

import com.kosmokamikaze.intervalapp.models.QuizDataModel
import com.kosmokamikaze.intervalapp.repository.QuizRepository

class DataBaseFiller {
    companion object {
        suspend fun addInitialValues(repository: QuizRepository) {
            repository.addQuiz(QuizDataModel("ЧИСТЫЕ КВИНТЫ", 0, 1, 5, 4))
            repository.addQuiz(QuizDataModel("ЧИСТЫЕ КВАРТЫ", 0, -1, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ ТЕРЦИИ", 0, 4, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ ТЕРЦИИ", 0, -3, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕПТИМЫ", 0, 5, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕПТИМЫ", 0, -2, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКУНДЫ", 0, 2, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКУНДЫ", 0, -5, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКСТЫ", 0, 3, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКСТЫ", 0, -4, 5, 4))
            repository.addQuiz(QuizDataModel("БОЛЬШИЕ СЕКСТЫ", 0, 3, 5, 4))
            repository.addQuiz(QuizDataModel("МАЛЫЕ СЕКСТЫ", 0, -4, 5, 4))
            repository.addQuiz(QuizDataModel("УМЕНЬШЕННЫЕ КВИНТЫ", 0, -6, 5, 4))
            repository.addQuiz(QuizDataModel("УВЕЛИЧЕННЫЕ КВАРТЫ", 0, 6, 5, 4))

            repository.addQuiz(QuizDataModel("ИНТЕРВАЛ ПО НОТАМ", 1, 0, 5, 4))

            repository.addQuiz(QuizDataModel("МАЖОРНЫЕ ТРЕЗВУЧИЯ",  2, 10, 5, 3))
            repository.addQuiz(QuizDataModel("МИНОРНЫЕ ТРЕЗВУЧИЯ",  2, 6, 5, 3))
            repository.addQuiz(QuizDataModel("MAJ7 АККОРДЫ",  2, 42, 5, 3))
            repository.addQuiz(QuizDataModel("MIN7 АККОРДЫ",  2, 25, 5, 3))
            repository.addQuiz(QuizDataModel("ДОМИНАНТНЫЕ АККОРДЫ",  2, 41, 5, 3))
            repository.addQuiz(QuizDataModel("MINMAJ АККОРДЫ",  2, 26, 5, 3))

            repository.addQuiz(QuizDataModel(0,"ИОНИЙСКИЙ ЛАД", 0, 4, 2726, 5, 9))
        }
    }
}