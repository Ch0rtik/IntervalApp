package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler
import com.kosmokamikaze.intervalapp.question.Question
import com.kosmokamikaze.intervalapp.question.QuestionGenerator

class Quiz (type: Int,
            option: Int,
            amountOfAnswers: Int,
            range: Int,
            mnh: MusicNameHandler
) {
    lateinit var currentQuestion: Question

    private val questionGenerator: QuestionGenerator = QuestionGenerator(type, option, amountOfAnswers, range, mnh)

    private var score = 0
    private var prevSubj: Int = 0


    fun askNewQuestion() {
        currentQuestion = questionGenerator.getNewQuestion(prevSubj)
        currentQuestion.ask()
        prevSubj = currentQuestion.subject
    }

    fun giveAnswer(btnId: Int): Int? {
        if (btnId == currentQuestion.rightButton) {
            score++
            return null
        }
        return score
    }
}