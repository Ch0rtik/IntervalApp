package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.questionmaker.QuestionMaker

class Quiz (
    private val questionMaker: QuestionMaker) {
    lateinit var currentQuest: Question

    private val amountOfAnswers = questionMaker.getAmountOfAnswers()

    private var score = 0
    private var prevSubj: Int = 0


    fun askNewQuestion() {
        currentQuest = Question()
        prevSubj = currentQuest.subject
    }

    fun giveAnswer(btnId: Int): Int? {
        if (btnId == currentQuest.rightButton) {
            score++
            return null
        }
        return score
    }

    inner class Question {
        val subjText: String
        val optionText: String
        val buttonTexts: List<String>
        val subject: Int

        var rightButton = (0 until amountOfAnswers).random()

        init {
            questionMaker.feedArguments(prevSubj, rightButton)
            subjText = questionMaker.getSubjectText()
            buttonTexts = questionMaker.getButtonTexts()
            optionText = questionMaker.getOptionText()
            subject = questionMaker.getSubject()
        }
    }
}