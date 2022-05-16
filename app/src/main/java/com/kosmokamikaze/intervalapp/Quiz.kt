package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.question.Question
import com.kosmokamikaze.intervalapp.question.QuestionGenerator

class Quiz (type: Int,
            option: Int,
            amountOfAnswers: Int,
            range: Int,
            mnh: MusicTheoryHandler
) {
    lateinit var currentQuestion: Question

    private val questionGenerator: QuestionGenerator = QuestionGenerator(type, option, amountOfAnswers, range, mnh)
    val amountOfAnswers = questionGenerator.amountOfAnswers

    private var score = 0
    private var prevSubj: Int = 0


    fun askNewQuestion() {
        currentQuestion = questionGenerator.getNewQuestion(prevSubj)
        currentQuestion.ask()
        prevSubj = currentQuestion.subject
    }

    fun giveAnswer(btnIds: Set<Int>): Int? {
        if (btnIds.containsAll(currentQuestion.correctButtons)) {
            score++
            return null
        }
        return score
    }
}