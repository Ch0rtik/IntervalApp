package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.questionmaker.QuestionMaker

class Quiz (
    private val fourAnswers: Boolean,
    private val questionMaker: QuestionMaker, private val range: Int) {
    lateinit var currentQuest: Question

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
        val buttonTexts = mutableListOf<String>()

        var subject = getRandomId()
        var rightButton: Int = 0


        private fun getRandomId(): Int = (-range..range).random()

        init {
            while (subject == prevSubj) {
                subject = getRandomId()
            }
            val ansAmount = if (fourAnswers) 4 else 9
            rightButton = (0 until ansAmount).random()
            val rightAnswer = questionMaker.getRightAnswer(subject)

            val takenIds = mutableSetOf(rightAnswer, subject)

            for (i in 0 until ansAmount) {
                if (i == rightButton) {
                    buttonTexts.add(questionMaker.getButtonText(rightAnswer))
                    continue
                }
                var currentAns = getRandomId()
                while (takenIds.contains(currentAns)) {
                    currentAns = getRandomId()
                }
                takenIds.add(currentAns)
                buttonTexts.add(questionMaker.getButtonText(currentAns))
            }

            subjText = questionMaker.getSubjectText(subject)
            optionText = questionMaker.getOptionText()
        }
    }
}