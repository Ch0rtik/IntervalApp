package com.kosmokamikaze.intervalapp.model.quiz.question

import com.kosmokamikaze.intervalapp.model.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.model.quiz.QuizTypes
import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.log
import kotlin.random.Random

class QuestionGenerator(
    private val type: QuizTypes,
    private val option: Int,
    val amountOfButtons: Int,
    private val range: Int
) {
    private lateinit var mth: MusicTheoryHandler

    val amountOfAnswers: Int = when (type) {
        QuizTypes.NOTE_FROM_INTERVAL -> 1
        QuizTypes.INTERVAL_FROM_NOTES -> 1
        QuizTypes.CHORD_FROM_NOTES -> 1
        QuizTypes.NOTES_FROM_CHORD -> ceil(log(option.toDouble(), 4.0)).toInt() + 1
        QuizTypes.NOTES_FROM_SCALE -> 7
    }

    private val random = Random(Calendar.getInstance().timeInMillis)

    fun setMusicTheoryHandler(mth: MusicTheoryHandler) {
        this.mth = mth
    }

    fun getNewQuestion(prevSubj: Int): Question {
        return when (type) {
            QuizTypes.NOTE_FROM_INTERVAL -> NoteFromInterval(prevSubj)
            QuizTypes.INTERVAL_FROM_NOTES -> IntervalFromNotes(prevSubj)
            QuizTypes.CHORD_FROM_NOTES -> ChordFromNotes(prevSubj)
            QuizTypes.NOTES_FROM_CHORD -> NotesFromChord(prevSubj)
            QuizTypes.NOTES_FROM_SCALE -> NotesFromScale(prevSubj)
        }
    }

    private abstract inner class AbstractQuestion(prevSubj: Int) : Question {
        final override val correctButtonNumbers: Set<Int>
        protected lateinit var rightAnswers: MutableSet<Int>
        final override val subject: Int
        override lateinit var buttonTexts: List<String>

        init {
            var subj = getRandomId(-range, range + 1)
            while (subj == prevSubj) {
                subj = getRandomId(-range, range + 1)
            }

            this.subject = subj

            val correctButtons = mutableSetOf<Int>()
            for (i in 0 until amountOfAnswers) {
                var buttonId = getRandomId(0, amountOfButtons)
                while (correctButtons.contains(buttonId)) {
                    buttonId = getRandomId(0, amountOfButtons)
                }
                correctButtons.add(buttonId)
            }

            this.correctButtonNumbers = correctButtons
        }

        override fun ask() {
            rightAnswers = generateRightAnswers()

            val takenSet = getTakenSet()
            val buttonTexts = mutableListOf<Int>()

            for (i in 0 until amountOfButtons) {
                if (correctButtonNumbers.contains(i)) {
                    val answer = rightAnswers.random()
                    rightAnswers.remove(answer)
                    buttonTexts.add(answer)
                    continue
                }
                var id = getNewId()
                while (takenSet.contains(id)) {
                    id = getNewId()
                }
                buttonTexts.add(id)
                takenSet.add(id)
            }

            this.buttonTexts = buttonTexts.map { getTextById(it) }
        }

        protected fun getRandomId(from: Int, until: Int): Int = random.nextInt(from, until)

        protected abstract fun generateRightAnswers(): MutableSet<Int>

        protected abstract fun getNewId(): Int

        protected abstract fun getTakenSet(): MutableSet<Int>

        protected abstract fun getTextById(id: Int): String
    }

    private inner class NoteFromInterval(prevSubj: Int) : AbstractQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject)
        override val optionText: String
            get() = mth.getIntervalNameAccusative(option)


        override fun generateRightAnswers(): MutableSet<Int> {
            return mutableSetOf(MusicTheoryHandler.getNoteFromInterval(subject, option))
        }

        override fun getNewId(): Int {
            return getRandomId(-range, range + 1)
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, rightAnswers.random(), subject + 7, subject - 7)
        }

        override fun getTextById(id: Int): String {
            return mth.getNoteName(id)
        }
    }

    private inner class IntervalFromNotes(prevSubj: Int) : AbstractQuestion(prevSubj) {
        override fun generateRightAnswers(): MutableSet<Int> {
            return mutableSetOf(subject)
        }

        override fun getNewId(): Int {
            return getRandomId(-range, range + 1)
        }

        override fun getTakenSet(): MutableSet<Int> {
            return mutableSetOf(subject, 0)
        }

        override fun getTextById(id: Int): String {
            return mth.getShortIntervalName(id)
        }

        private fun getTwoNotes(): String {
            val first = getRandomId(-range, range + 1)
            val second = MusicTheoryHandler.getNoteFromInterval(first, subject)
            return (mth.getNoteName(first) + ", " + mth.getNoteName(second))
        }

        override val subjectText: String
            get() = getTwoNotes()
        override val optionText: String
            get() = mth.interval

    }

    private inner class ChordFromNotes(prevSubj: Int) : AbstractQuestion(prevSubj) {
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
            get() = chord.shuffled().joinToString(", ") { mth.getNoteName(it) }
        override val optionText: String
            get() = mth.getChordType(option)
    }

    private abstract inner class AbstractNotesQuestion(prevSubj: Int) : AbstractQuestion(prevSubj) {
        protected val notes = MusicTheoryHandler.buildChord(subject, option)

        override fun generateRightAnswers(): MutableSet<Int> {
            return notes.toMutableSet()
        }

        override fun getNewId(): Int {
            return getRandomId(-range, range + 1)
        }

        override fun getTakenSet(): MutableSet<Int> {
            return notes.toMutableSet()
        }

        override fun getTextById(id: Int): String {
            return mth.getNoteName(id)
        }
    }

    private inner class NotesFromChord(prevSubj: Int) : AbstractNotesQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject)
        override val optionText: String
            get() = mth.getChordType(option)
    }

    private inner class NotesFromScale(prevSubj: Int) : AbstractNotesQuestion(prevSubj) {
        override val subjectText: String
            get() = mth.getNoteName(subject)
        override val optionText: String
            get() = mth.getScaleName(option)
    }
}