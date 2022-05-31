package com.kosmokamikaze.intervalapp.quiz

import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.quiz.question.Question
import com.kosmokamikaze.intervalapp.quiz.question.QuestionGenerator

class Quiz (type: Int,
            option: Int,
            amountOfButtons: Int,
            range: Int,
            mth: MusicTheoryHandler
) {
    lateinit var currentQuestion: Question

    private val questionGenerator: QuestionGenerator = QuestionGenerator(type, option, amountOfButtons, range, mth)
    val amountOfAnswers = questionGenerator.amountOfAnswers


    private var mutScore = 0
    private var prevSubj: Int = (-range..range).random()

    val score: Int get() = mutScore


    fun askNewQuestion() {
        currentQuestion = questionGenerator.getNewQuestion(prevSubj)
        currentQuestion.ask()
        prevSubj = currentQuestion.subject
    }

    fun submitAnswer(chosenButtons: Set<Int>): Int? {
        if (chosenButtons.containsAll(currentQuestion.correctButtons)) {
            mutScore++
            return null
        }
        return mutScore
    }
}