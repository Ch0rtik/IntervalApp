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
            arrayOf(),
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

    @Test
    fun noteFromInterval() {
        val option = 1
        val quiz = Quiz(QuizData(0, 0, QuizTypes.NOTE_FROM_INTERVAL, option, 5, 4))
        quiz.setMusicTheoryHandler(mth)
        quiz.askNewQuestion()
        val answerId = MusicTheoryHandler.getNoteFromInterval(quiz.subject, option)
        val rightAnswer = mth.getNoteName(answerId)
        val rightButtonNumber = quiz.currentQuestion.buttonTexts.indexOf(rightAnswer)

        assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
        assert(quiz.score == 1)
    }

    @Test
    fun intervalQuizOption() {
        val option = 1
        val quiz = Quiz(QuizData( QuizTypes.NOTE_FROM_INTERVAL, option, 5, 4))
        quiz.setMusicTheoryHandler(mth)
        quiz.askNewQuestion()

        assert(quiz.currentQuestion.optionText == mth.getIntervalNameAccusative(option))
    }

    @Test
    fun intervalFromNotes() {
        val options = setOf(1, 2, 3, 4, 5, 6, 7, 8)
        for (option in options) {
            val quiz = Quiz(QuizData(QuizTypes.INTERVAL_FROM_NOTES, option, 5, 4))
            quiz.setMusicTheoryHandler(mth)
            quiz.askNewQuestion()
            val subjectNotes = quiz.currentQuestion.subjectText.split(", ")
            val rightAnswer = mth.getShortIntervalName(mth.getIntervalFromNotes(subjectNotes[0], subjectNotes[1]))
            val rightButtonNumber =
                quiz.currentQuestion.buttonTexts.indexOf(rightAnswer)

            assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
        }
    }

    @Test
    fun chordFromNotes() {
        val option = 10
        val quiz = Quiz(QuizData(QuizTypes.CHORD_FROM_NOTES, option, 5, 3))
        quiz.setMusicTheoryHandler(mth)
        quiz.askNewQuestion()
        val answerId = quiz.subject
        val rightButtonNumber = quiz.currentQuestion.buttonTexts.indexOf(mth.getNoteName(answerId))

        assert(quiz.submitAnswer(setOf(rightButtonNumber)) == null)
    }

    @Test
    fun notesFromChord() {
        val option = 10
        val quiz = Quiz(QuizData(QuizTypes.NOTES_FROM_CHORD, option, 5, 9))
        quiz.setMusicTheoryHandler(mth)
        quiz.askNewQuestion()
        val answerIds = MusicTheoryHandler.buildChord(quiz.currentQuestion.subject, option)
        val rightButtonNumbers = mutableSetOf<Int>()
        for (answerId in answerIds) {
            rightButtonNumbers.add(quiz.currentQuestion.buttonTexts.indexOf(mth.getNoteName(answerId)))
        }

        assert(quiz.submitAnswer(rightButtonNumbers) == null)
    }

    @Test
    fun notesFromScale() {
        val options = setOf(2726, 1637, 1621, 1638, 2662, 2730, 1365)
        for(option in options) {
            val quiz = Quiz(QuizData(QuizTypes.NOTES_FROM_SCALE, option, 5, 9))
            quiz.setMusicTheoryHandler(mth)
            quiz.askNewQuestion()
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

            assert(quiz.currentQuestion.optionText == mth.getScaleName(option))
            assert(quiz.submitAnswer(rightButtonNumbers) == null)
        }
    }
}