package com.kosmokamikaze.intervalapp.musical


class MusicTheoryHandler(musicalNames: MusicalNames) {
    private object Constants {
        const val D_POSITION = 17
        const val PRIMA_POSITION = 7
    }


    private val noteNames = musicalNames.noteNames
    private val intervalNames = musicalNames.intervalNames
    private val shortIntervalNames = musicalNames.shortIntervalNames


    fun getNoteList(range: Int): List<String> {
        return noteNames
            .slice(Constants.D_POSITION - range..Constants.D_POSITION + range)
    }

    fun getNoteName(relId: Int): String {
        return noteNames[relId + Constants.D_POSITION]
    }

    fun getIntervalName(relId: Int): String {
        return intervalNames[relId + Constants.PRIMA_POSITION]
    }

    fun getShortIntervalName(relId: Int): String {
        return shortIntervalNames[relId + Constants.PRIMA_POSITION]
    }

    fun getNoteFromInterval(rootId: Int, interval: Int): Int {
        return rootId + interval
    }

    companion object ChordBuilder {
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