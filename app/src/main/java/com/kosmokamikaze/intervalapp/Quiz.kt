package com.kosmokamikaze.intervalapp

import android.view.View

class Quiz (
    fourAnswers: Boolean,
    private val activity: QuizActivity) {
    private val ansLyts = activity.ansLyts
    private val ansBttns = activity.ansBttns
    private val subText = activity.subjText
    private val optText = activity.optText

    private lateinit var currentQuest: Question
    private var prevSubjId = 0

    private var score = 0
    private var possibleBttns: Set<Int> = setOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private var questRange: Int = 5

    init {
        if (fourAnswers) {
            possibleBttns = setOf(0, 2, 6, 8)
            for (i in ansBttns.indices) {
                if (!possibleBttns.contains(i)) {
                    ansBttns[i].visibility = View.INVISIBLE
                    ansBttns[i].isClickable = false
                    ansLyts[i].visibility = View.INVISIBLE
                }
            }
        }
    }

    private inner class Question(prevSubjId: Int) {
        val rightBtnId = possibleBttns.random()
        private var questSubjId = getRandomId()
        private val rightAnsId: Int
        private val usedAnsIds = mutableSetOf<Int>()
        init {
            while (questSubjId == prevSubjId) {
                questSubjId = getRandomId()
            }
            rightAnsId = getRightAns(questSubjId)

            usedAnsIds.add(questSubjId)
            usedAnsIds.add(rightAnsId)

            subText.text = getText(questSubjId)
            ansBttns[rightBtnId].text = getText(rightAnsId)

            for (i in possibleBttns) {
                if (i == rightBtnId) continue
                var currentId = getRandomId()
                while (usedAnsIds.contains(currentId)) {
                    currentId = getRandomId()
                }
                usedAnsIds.add(currentId)
                ansBttns[i].text = getText(currentId)
            }
        }

        fun getRandomId(): Int {
            return (-questRange..questRange).random()
        }

        fun getText(relId: Int): String {
            //TEMPORARY !!!
            return activity.resources.getStringArray(R.array.note_names)[relId + 17]
        }

        fun getRightAns(relId: Int): Int {
            //TEMPORARY !!!
            return relId + 1
        }

        fun ask(): Int {
            return questSubjId
        }

    }


    fun askNewQuestion() {
        currentQuest = Question(prevSubjId)
        prevSubjId = currentQuest.ask()
    }

    fun giveAnswer(btnId: Int): Int? {
        if (btnId == currentQuest.rightBtnId) {
            score++
            return null
        }
        return score
    }
}