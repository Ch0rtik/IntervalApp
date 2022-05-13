package com.kosmokamikaze.intervalapp.questionmaker

import android.content.Context
import com.kosmokamikaze.intervalapp.R

class QuestionMakerDraft(context: Context) {
    private lateinit var subjText: String
    private lateinit var optText: String
    private lateinit var buttonTexts: List<String>
    private var rightAns: Int = 0
    private lateinit var optionText: String

    private lateinit var getSubjTextVar: (relId: Int) -> String
    private lateinit var getOptionTextVar: (option: Int) -> String
    private lateinit var getAnsTextVar: (relId: Int) -> String
    private lateinit var getRightAnsVar: (relId: Int, option: Int) -> Int
    private val mnh = MusicNameHandler(context)

    private class MusicNameHandler(context: Context) {
        private object Constants {
            const val D_POSITION = 17
            const val PRIMA_POSITION = 7
        }
        private val noteNames = context.resources.getStringArray(R.array.note_names)
        private val intervalNames = context.resources.getStringArray(R.array.interval_names)

        fun getNoteList(range: Int): List<String> {
            return noteNames
                .slice(Constants.D_POSITION-range..Constants.D_POSITION+range)
        }

        fun getNoteName(relId: Int): String {
            return noteNames[relId + Constants.D_POSITION]
        }

        fun getIntervalName(relId: Int): String {
            return intervalNames[relId + Constants.PRIMA_POSITION]
        }

        fun getNoteFromInterval(rootId: Int, interval: Int): Int {
            return rootId + interval
        }

        companion object ChordBuilder {
            fun buildChord(relId: Int, chordType: Array<Boolean>): Set<Int> {
                val chord = mutableSetOf(relId)
                for(i in chordType.indices) {
                    val chordNote = relId + 4 + (i / 2) - 3*(i % 2) - (if (chordType[i]) 0 else 1)*7
                    chord.add(chordNote)
                }
                return chord
            }
        }
    }

    fun getSubjText(relId: Int): String {
        return getSubjTextVar(relId)
    }

    fun getOptionText(relId: Int): String {
        return getOptionTextVar(relId)
    }

    fun getAnsText(relId: Int): String {
        return getAnsTextVar(relId)
    }

    fun getRightAns(relId: Int, option: Int): Int {
        return getRightAnsVar(relId, option)
    }

    private fun getNoteFromInterval(subjId: Int, option: Option, range: Int) {
        subjText = mnh.getNoteName(subjId)
        rightAns = mnh.getNoteFromInterval(subjId, option.getInterval())
        optionText = option.getText()
        buttonTexts = mnh.getNoteList(range)
    }

    private fun getChordFromNotes(subjId: Int, option: Option, range: Int) {
        subjText =
            MusicNameHandler.buildChord(subjId, option.getChord())
                .shuffled()
                .joinToString(", ")

        rightAns = subjId
        optionText = option.getText()
        buttonTexts = MusicNameHandler.buildChord(subjId, option.getChord())
            .shuffled().map { mnh.getNoteName(it) }

    }
}

