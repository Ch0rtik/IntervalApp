package com.kosmokamikaze.intervalapp.model.quiz

import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.model.quiz.question.Question
import com.kosmokamikaze.intervalapp.model.quiz.question.QuestionGenerator


class Quiz(
    quizData: QuizData
) {

    lateinit var currentQuestion: Question

    private val questionGenerator: QuestionGenerator = QuestionGenerator(
        quizData.type,
        quizData.option,
        quizData.amountOfButtons,
        quizData.range
    )
    val amountOfAnswers = questionGenerator.amountOfAnswers


    private var mutScore = 0
    private var prevSubj: Int = (-quizData.range..quizData.range).random()

    val score: Int get() = mutScore

    fun setMusicTheoryHandler(mth: MusicTheoryHandler) {
        questionGenerator.setMusicTheoryHandler(mth)
    }


    fun askNewQuestion() {
        currentQuestion = questionGenerator.getNewQuestion(prevSubj)
        currentQuestion.ask()
        prevSubj = currentQuestion.subject
    }

    fun submitAnswer(chosenButtonNumbers: Set<Int>): Int? {
        if (chosenButtonNumbers.containsAll(currentQuestion.correctButtonNumbers)) {
            mutScore++
            return null
        }
        return mutScore
    }

}