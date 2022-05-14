package com.kosmokamikaze.intervalapp.questionmaker

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler

class DemoQMFactory(private val type: Int,
                    private val option: Int,
                    private val amountOfAnswers: Int,
                    private val range: Int,
                    private val mnh: MusicNameHandler) {

    fun getNewQuestion(prevSubj: Int): Question {
        return when(type) {
            0 -> IntervalQuestion(prevSubj)
            else -> IntervalQuestion(prevSubj)
        }
    }

    private abstract inner class AbstractQuestion(prevSubj: Int): Question {
        final override val rightButton: Int = (0..amountOfAnswers).random()
        final override val subject: Int
        final override val buttonTexts: List<String>
        protected var rightAns = 0

        init {
            var subj = getRandomId()
            while (subj == prevSubj) {
                subj = getRandomId()
            }
            this.subject = subj
            rightAns = getRightAnswer()

            val takenSet = getTakenSet()
            val list = mutableListOf<Int>()
            for (i in 0 until amountOfAnswers) {
                if (i == rightButton) {
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

    private inner class IntervalQuestion(prevSubj: Int): AbstractQuestion(prevSubj) {
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
}

interface Question {
    val rightButton: Int
    val subject: Int
    val subjectText: String
    val buttonTexts: List<String>
    val optionText: String
}