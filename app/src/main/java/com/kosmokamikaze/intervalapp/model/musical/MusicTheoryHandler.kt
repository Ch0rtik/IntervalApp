package com.kosmokamikaze.intervalapp.model.musical


class MusicTheoryHandler(musicalNames: MusicalNames) {
    private object Constants {
        const val D_POSITION = 17
        const val PRIMA_POSITION = 7
    }
    private val noteNames = musicalNames.noteNames
    private val intervalNames = musicalNames.intervalNames
    private val intervalNamesAccusative = musicalNames.intervalNamesAccusative
    private val shortIntervalNames = musicalNames.shortIntervalNames
    private val scaleNames = musicalNames.scaleNames
    private val chordTypes = musicalNames.chordTypeNames
    val interval = musicalNames.interval

    fun getNoteName(relId: Int): String {
        return noteNames[relId + Constants.D_POSITION]
    }

    fun getIntervalName(option: Int): String {
        return intervalNames[option + Constants.PRIMA_POSITION]
    }

    fun getIntervalFromNotes(first: String, second: String): Int {
        return getIntervalFromNotes(getNoteId(first), getNoteId(second))
    }

    fun getIntervalNameAccusative(option: Int): String {
        return intervalNamesAccusative[option + Constants.PRIMA_POSITION]
    }

    fun getShortIntervalName(relId: Int): String {
        return shortIntervalNames[relId + Constants.PRIMA_POSITION]
    }

    fun getNoteId(noteName: String): Int {
        return noteNames.indexOf(noteName) - Constants.D_POSITION
    }

    fun getChordType(option: Int): String {
        return when(option) {
            10 -> chordTypes[0]
            6 -> chordTypes[1]
            42 -> chordTypes[2]
            25 -> chordTypes[3]
            41 -> chordTypes[4]
            26 -> chordTypes[5]
            else -> ""
        }
    }

    fun getScaleName(option: Int): String {
        return when(option) {
            2726 -> scaleNames[0]
            1637 -> scaleNames[1]
            1621 -> scaleNames[2]
            1638 -> scaleNames[3]
            2662 -> scaleNames[4]
            2730 -> scaleNames[5]
            1365 -> scaleNames[6]
            else -> ""
        }
    }

    companion object {
        fun getNoteFromInterval(rootId: Int, interval: Int): Int {
            return rootId + interval
        }

        fun getIntervalFromNotes(first: Int, second: Int): Int {
            return second - first
        }

        private fun buildChord(relId: Int, chordType: List<Byte>): List<Int> {
            val chord = mutableListOf(relId)
            for (i in chordType.indices) {
                val chordNote = relId + 4 + (i / 2) - 3 * (i % 2) + (chordType[i] - 2) * 7
                chord.add(chordNote)
            }
            return chord
        }

        fun buildChord(relId: Int, chordType: Int): List<Int> {
            return buildChord(relId, toQuaternaryList(chordType))
        }

        fun buildScale(relId: Int, scaleType: Int): List<Int> {
            return buildChord(relId, scaleType)
        }

        private fun toQuaternaryList(num: Int): List<Byte> {
            val list = mutableListOf<Byte>()
            var number = num
            var currentPower = 1
            while (number > currentPower) {
                currentPower *= 4
            }
            currentPower /= 4
            while (currentPower != 0) {
                list.add((number / currentPower).toByte())
                number %= currentPower
                currentPower /= 4
            }
            return list
        }
    }
}