package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.model.quiz.Quiz
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.QuizTypes
import com.kosmokamikaze.intervalapp.model.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.model.musical.MusicalNames
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class QuizUnitTests {

    private val mth = MusicTheoryHandler(
        MusicalNames(
            arrayOf(
                "F♭♭", "C♭♭", "G♭♭", "D♭♭", "A♭♭", "E♭♭", "B♭♭",
                "F♭", "C♭", "G♭", "D♭", "A♭", "E♭", "B♭",
                "F", "C", "G", "D", "A", "E", "B",
                "F#", "C#", "G#", "D#", "A#", "E#", "B#",
                "F##", "C##", "G##", "D##", "A##", "E##", "B##"
            ),
            arrayOf(),
            arrayOf(
                "Уменьшенную приму", "Уменьшенную квинту",
                "Малую секунду", "Малую сексту", "Малую терцию", "Малую септиму",
                "Чистую кварту", "Приму", "Чистую квинту",
                "Большую секунду", "Большую сексту", "Большую терцию", "Большую септиму",
                "Увеличенную кварту", "Увеличенную приму", "Увеличенную квинту",
                "Увеличенную секунду", "Увеличенную сексту", "Увеличенную терцию",
                "Увеличенную септиму",
                "Дважды увеличеннную кварту"
            ),
            arrayOf("Мажорное трезвучие",
                "Минорное трезвучие",
                "Maj7 аккорд",
                "min7 аккорд",
                "доминантные аккорд",
                "minMaj7 акорд"),
            arrayOf(
                "d1", "d5",
                "m2", "m6", "m3", "m7",
                "P4", "P1", "P5",
                "M2", "M6", "M3", "M7",
                "A4", "A1", "A5", "A2", "A6", "A3", "A7",
                "AA5"
            ),
            arrayOf(
                "ИОНИЙСКИЙ ЛАД",
                "ЭОЛИЙСКИЙ ЛАД",
                "ФРИГИЙСКИЙ ЛАД",
                "ДОРИЙСКИЙ ЛАД",
                "МИКСОЛИДИЙСКИЙ ЛАД",
                "ЛИДИЙСКИЙ ЛАД",
                "ЛОКРИЙСКИЙ ЛАД"
            ),
            "интервал"
        )
    )

    private fun generateQuiz(type: QuizTypes, option: Int, amountOfButtons: Int): Quiz {
        val quiz = Quiz(QuizData(type, option, 5, amountOfButtons))
        quiz.setMusicTheoryHandler(mth)
        quiz.askNewQuestion()
        return quiz
    }

    private val intervalOptions = setOf(1, 2, 3, 4, 5, 6, 7, 8)

    @Test
    fun noteFromInterval() {
        for (option in intervalOptions) {
            val quiz = generateQuiz(QuizTypes.NOTE_FROM_INTERVAL, option, 4)
            val answerId = MusicTheoryHandler.getNoteFromInterval(quiz.subject, option)
            val rightAnswer = mth.getNoteName(answerId)
            val rightButtonNumber = quiz.currentQuestion.buttonTexts.indexOf(rightAnswer)

            assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
            assert(quiz.score == 1)
        }
    }

    @Test
    fun noteFromIntervalUI() {
        for (option in intervalOptions) {
            val quiz = generateQuiz(QuizTypes.NOTE_FROM_INTERVAL, option, 4)

            assert(quiz.currentQuestion.optionText == mth.getIntervalNameAccusative(option))
            assert(quiz.currentQuestion.subjectText == mth.getNoteName(quiz.currentQuestion.subject))
        }
    }



    @Test
    fun intervalFromNotes() {
        for (option in intervalOptions) {
            val quiz = generateQuiz(QuizTypes.INTERVAL_FROM_NOTES, option, 4)
            val subjectNotes = quiz.currentQuestion.subjectText.split(", ")
            val rightAnswer = mth.getShortIntervalName(mth.getIntervalFromNotes(subjectNotes[0], subjectNotes[1]))
            val rightButtonNumber =
                quiz.currentQuestion.buttonTexts.indexOf(rightAnswer)

            assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
        }
    }

    @Test
    fun intervalFromNotesUI() {
        for (option in intervalOptions) {
            val quiz = generateQuiz(QuizTypes.INTERVAL_FROM_NOTES, option, 4)

            assert(quiz.currentQuestion.optionText == mth.interval)
        }
    }

    private val chordOptions = setOf(10, 6, 45, 25, 41, 26)

    @Test
    fun chordFromNotes() {
        for (option in chordOptions) {
            val quiz = generateQuiz(QuizTypes.CHORD_FROM_NOTES, option, 3)
            val answerId = quiz.currentQuestion.subject
            val rightButtonNumber =
                quiz.currentQuestion.buttonTexts.indexOf(mth.getNoteName(answerId))

            assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
        }
    }

    @Test
    fun chordFromNotesUI() {
        for (option in chordOptions) {
            val quiz = generateQuiz(QuizTypes.CHORD_FROM_NOTES, option, 3)

            assert(quiz.currentQuestion.optionText == mth.getChordType(option))
        }
    }

    @Test
    fun notesFromChord() {
        for (option in chordOptions) {
            val quiz = generateQuiz(QuizTypes.NOTES_FROM_CHORD, option, 9)
            val answerIds = MusicTheoryHandler.buildChord(quiz.currentQuestion.subject, option)
            val rightButtonNumbers = mutableSetOf<Int>()
            for (answerId in answerIds) {
                rightButtonNumbers.add(
                    quiz.currentQuestion.buttonTexts.indexOf(
                        mth.getNoteName(
                            answerId
                        )
                    )
                )
            }

            assert(quiz.submitAnswer(rightButtonNumbers) == null)
        }
    }

    @Test
    fun notesFromChordUI() {
        for (option in chordOptions) {
            val quiz = generateQuiz(QuizTypes.NOTES_FROM_CHORD, option, 9)

            assert(quiz.currentQuestion.optionText == mth.getChordType(option))
            assert(quiz.currentQuestion.subjectText == mth.getNoteName(quiz.currentQuestion.subject))
        }
    }

    private val notesFromScaleOptions = setOf(2726, 1637, 1621, 1638, 2662, 2730, 1365)

    @Test
    fun notesFromScale() {
        for(option in notesFromScaleOptions) {
            val quiz = generateQuiz(QuizTypes.NOTES_FROM_SCALE, option, 9)
            val answerIds = MusicTheoryHandler.buildScale(quiz.currentQuestion.subject, option)
            val rightButtonNumbers = mutableSetOf<Int>()
            for (answerId in answerIds) {
                rightButtonNumbers.add(
                    quiz.currentQuestion.buttonTexts.indexOf(
                        mth.getNoteName(
                            answerId
                        )
                    )
                )
            }

            assert(quiz.submitAnswer(rightButtonNumbers) == null)
        }
    }

    @Test
    fun notesFromScaleUI() {
        for(option in notesFromScaleOptions) {
            val quiz = generateQuiz(QuizTypes.NOTES_FROM_SCALE, option, 9)

            assert(quiz.currentQuestion.optionText == mth.getScaleName(option))
            assert(quiz.currentQuestion.subjectText == mth.getNoteName(quiz.currentQuestion.subject))
        }
    }
}