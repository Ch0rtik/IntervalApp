package com.kosmokamikaze.intervalapp.quiz.question

import android.util.Log
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import java.lang.IllegalArgumentException
import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.log
import kotlin.random.Random

class QuestionGenerator (private val type: Int,
                         private val option: Int,
                         val amountOfButtons: Int,
                         private val range: Int,
                         private val mth: MusicTheoryHandler) {
    val amountOfAnswers: Int = when(type) {
        0 -> 1
        1 -> 1
        2 -> 1
        3 -> ceil(log(option.toDouble(), 4.0)).toInt() + 1
        4 -> 7
        else -> 1
    }

    private val random = Random(Calendar.getInstance().timeInMillis)

    fun getNewQuestion(prevSubj: Int): Question {
        return when(type) {
            0 -> NoteFromInterval(prevSubj)
            1 -> IntervalFromNotes(prevSubj)
            2 -> ChordFromNotes(prevSubj)
            3 -> NotesFromChord(prevSubj)
            4 -> NotesFromScale(prevSubj)

            else -> throw IllegalArgumentException("Wrong type")
        }
    }

    private abstract inner class AbstractQuestion(prevSubj: Int): Question {
        final override val correctButtons: Set<Int>
        protected lateinit var rightAnswers: MutableSet<Int>
        final override val subject: Int
        override lateinit var buttonTexts: List<String>

        init {
            var subj = getRandomId()
            Log.d("quiz", "prev: " + mth.getNoteName(prevSubj))
            Log.d("quiz", mth.getNoteName(subj))
            Log.d("quiz", (subj == prevSubj).toString())
            while (subj == prevSubj) {
                subj = getRandomId()
                Log.d("quiz", mth.getNoteName(subj))
                Log.d("quiz", (subj == prevSubj).toString())

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
            rightAnswers = generateRightAnswers()

            val takenSet = getTakenSet()
            val list = mutableListOf<Int>()
            for (i in 0 until amountOfButtons) {
                if (correctButtons.contains(i)) {
                    val answer = rightAnswers.random()
                    rightAnswers.remove(answer)
                    list.add(answer)
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

        protected fun getRandomId(): Int = random.nextInt(-range, range)

        protected abstract fun generateRightAnswers(): MutableSet<Int>

        protected abstract fun getNewId(): Int

        protected abstract fun getTakenSet(): MutableSet<Int>

        protected abstract fun getTextById(id: Int): String
    }

    private inner class NoteFromInterval(prevSubj: Int): AbstractQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject)
        override val optionText: String
            get() = mth.getIntervalName(option)


        override fun generateRightAnswers(): MutableSet<Int> {
            return mutableSetOf(mth.getNoteFromInterval(subject, option))
        }

        override fun getNewId(): Int {
            return getRandomId()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, rightAnswers.random(), subject + 7, subject - 7)
        }

        override fun getTextById(id: Int): String {
            return mth.getNoteName(id)
        }
    }

    private inner class IntervalFromNotes(prevSubj: Int): AbstractQuestion(prevSubj) {
        override fun generateRightAnswers(): MutableSet<Int> {
            return mutableSetOf(subject)
        }

        override fun getNewId(): Int {
            return getRandomId()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, 0)
        }

        override fun getTextById(id: Int): String {
            return mth.getShortIntervalName(id)
        }

        private fun getTwoNotes(): String {
            val first = getRandomId()
            val second = mth.getNoteFromInterval(first, subject)
            return (mth.getNoteName(first) + ", " + mth.getNoteName(second))
        }

        override val subjectText: String
            get() = getTwoNotes()
        override val optionText: String
            get() = "ИНТЕРВАЛ"

    }

    private inner class ChordFromNotes(prevSubj: Int): AbstractQuestion(prevSubj) {
        private val chord = MusicTheoryHandler.buildChord(subject, option)

        override fun generateRightAnswers(): MutableSet<Int> {
            return mutableSetOf(subject)
        }

        override fun getNewId(): Int {
            return chord.random()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject)
        }

        override fun getTextById(id: Int): String {
            return mth.getNoteName(id)
        }

        override val subjectText: String
            get() = chord.shuffled().joinToString(", ") { mth.getNoteName(it)}
        override val optionText: String
            get() = "TODO"
    }

    private abstract inner class AbstractNotesQuestion(prevSubj: Int): AbstractQuestion(prevSubj) {
        protected val notes = MusicTheoryHandler.buildChord(subject, option)

        override fun generateRightAnswers(): MutableSet<Int> {
            return notes.toMutableSet()
        }

        override fun getNewId(): Int {
            return getRandomId()
        }

        override fun getTakenSet(): MutableSet<Int> {
            return notes.toMutableSet()
        }

        override fun getTextById(id: Int): String {
            return mth.getNoteName(id)
        }
    }

    private inner class NotesFromChord(prevSubj: Int): AbstractNotesQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject) //!!!
        override val optionText: String
            get() = "ноты"
    }

    private inner class NotesFromScale(prevSubj: Int): AbstractNotesQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject) //!!!
        override val optionText: String
            get() = "ноты"
    }
}