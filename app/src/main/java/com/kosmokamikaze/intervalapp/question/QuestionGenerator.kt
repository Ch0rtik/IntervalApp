package com.kosmokamikaze.intervalapp.question

import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import kotlin.math.ceil
import kotlin.math.log

class QuestionGenerator (private val type: Int,
                         private val option: Int,
                         val amountOfButtons: Int,
                         private val range: Int,
                         private val mnh: MusicTheoryHandler) {
    val amountOfAnswers: Int = when(type) {
        0 -> 1
        1 -> 1
        2 -> 1
        3 -> ceil(log(option.toDouble(), 4.0)).toInt()
        else -> 1
    }

    fun getNewQuestion(prevSubj: Int): Question {
        return when(type) {
            0 -> {
                NoteFromInterval(prevSubj)
            }
            1 -> ChordFromNotes(prevSubj)
            2 -> IntervalFromNotes(prevSubj)
            else -> NoteFromInterval(prevSubj)
        }
    }

    private abstract inner class AbstractQuestion(prevSubj: Int): Question {
        final override val correctButtons: Set<Int>
        final val rightAnswers = mutableSetOf<Int>()
        final override val rightButton: Int = (0 until amountOfButtons).random()
        final override val subject: Int
        override lateinit var buttonTexts: List<String>
        protected var rightAns = 0

        init {
            var subj = getRandomId()
            while (subj == prevSubj) {
                subj = getRandomId()
            }

            this.subject = subj

            val correctButtons = mutableSetOf<Int>()
            for (i in 0 until amountOfAnswers) {
                var buttonId = (0 until amountOfButtons).random()
                while (correctButtons.contains(buttonId)) {
                    buttonId = (0 until amountOfButtons).random()
                }
                correctButtons.add(buttonId)
            }

            this.correctButtons = correctButtons
        }

        override fun ask() {
            rightAns = getRightAnswer()

            val takenSet = getTakenSet()
            val list = mutableListOf<Int>()
            for (i in 0 until amountOfButtons) {
                if (correctButtons.contains(i)) {
                    val answer = rightAnswers.random()
                    list.add(rightAns)
                    continue
                }
                var id = getNewId()
                while (takenSet.contains(id)) {
                    id = getNewId()
                }
                list.add(id)
                takenSet.add(id)
            }
            buttonTexts = list.map { getTextById(it) }
        }

        protected fun getRandomId(): Int = (-range..range).random()

        protected abstract fun getRightAnswer(): Int

        protected abstract fun getNewId(): Int

        protected abstract fun getTakenSet(): MutableSet<Int>

        protected abstract fun getTextById(id: Int): String
    }

    private inner class NoteFromInterval(prevSubj: Int): AbstractQuestion(prevSubj) {
        override val subjectText: String
            get() = mnh.getNoteName(subject)
        override val optionText: String
            get() = mnh.getIntervalName(option)

        override fun getRightAnswer(): Int {
            return mnh.getNoteFromInterval(subject, option)
        }

        override fun getNewId(): Int {
            return getRandomId()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, rightAns, subject + 7, subject - 7)
        }

        override fun getTextById(id: Int): String {
            return mnh.getNoteName(id)
        }
    }

    private inner class IntervalFromNotes(prevSubj: Int): AbstractQuestion(prevSubj) {
        override fun getRightAnswer(): Int {
            return subject
        }

        override fun getNewId(): Int {
            return getRandomId()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, 0)
        }

        override fun getTextById(id: Int): String {
            return mnh.getShortIntervalName(id)
        }

        private fun getTwoNotes(): String {
            val first = getRandomId()
            val second = mnh.getNoteFromInterval(first, subject)
            return (mnh.getNoteName(first) + ", " + mnh.getNoteName(second))
        }

        override val subjectText: String
            get() = getTwoNotes()
        override val optionText: String
            get() = "ИНТЕРВАЛ"

    }

    private inner class ChordFromNotes(prevSubj: Int): AbstractQuestion(prevSubj) {
        private val chord = MusicTheoryHandler.buildChord(subject, option)

        override fun getRightAnswer(): Int {
            return subject
        }

        override fun getNewId(): Int {
            return chord.random()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject)
        }

        override fun getTextById(id: Int): String {
            return mnh.getNoteName(id)
        }

        override val subjectText: String
            get() = chord.shuffled().joinToString(", ") { mnh.getNoteName(it)}
        override val optionText: String
            get() = "TODO"
    }
}