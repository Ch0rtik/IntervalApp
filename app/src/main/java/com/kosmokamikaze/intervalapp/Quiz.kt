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
    private val amountOfAnswers = questionGenerator.amountOfAnswers

    private val chosenButtons = mutableSetOf<Int>()

    private var score = 0
    private var prevSubj: Int = (-range..range).random()


    fun askNewQuestion() {
        chosenButtons.clear()
        currentQuestion = questionGenerator.getNewQuestion(prevSubj)
        currentQuestion.ask()
        prevSubj = currentQuestion.subject
    }

    fun submitAnswer(): Int? {
        if (chosenButtons.containsAll(currentQuestion.correctButtons)) {
            score++
            return null
        }
        return score
    }

    fun chooseAnswer(index: Int): Boolean {
        if (!chosenButtons.contains(index)) {
            if (chosenButtons.size < amountOfAnswers) {
                chosenButtons.add(index)
                return true
            }
            return false
        }
        chosenButtons.remove(index)
        return false
    }

    fun isSubmittable(): Boolean = chosenButtons.size == amountOfAnswers
}