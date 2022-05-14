package com.kosmokamikaze.intervalapp.questionmaker

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler

class QMFactory(private val mnh: MusicNameHandler) {
    private var range = 0
    private var amountOfAnswers = 0

    fun getQuestionMaker(type: Int,
                         option: Int,
                         amountOfAnswers: Int,
                         range: Int): QuestionMaker {
        this.amountOfAnswers = amountOfAnswers
        this.range = range
        return when(type) {
            0 -> IntervalQM(option, amountOfAnswers, range, mnh)
            1 -> ChordQM(option, amountOfAnswers, range, mnh)
            else -> IntervalQM(option, amountOfAnswers, range, mnh)
        }
    }

}