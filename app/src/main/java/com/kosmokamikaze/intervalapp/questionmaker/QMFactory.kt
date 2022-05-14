package com.kosmokamikaze.intervalapp.questionmaker

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler

class QMFactory(private val mnh: MusicNameHandler) {
    private var range = 0
    private var amountOfAnswrs = 0

    private fun getRandomId(): Int = (-range..range).random()

    fun getQuestionMaker(type: Int,
                         option: Int,
                         amountOfAnswers: Int,
                         range: Int): QuestionMaker {
        this.amountOfAnswrs = amountOfAnswers
        this.range = range
        return when(type) {
            0 -> getIntervalQM(option)
            1 -> getChordQM(option)
            else -> getIntervalQM(option)
        }
    }

    private fun getIntervalQM(option: Int): QuestionMaker {
        val getNewSubj: () -> Int = { getRandomId() }
        val getSubjTxt = mnh::getNoteName
        val getRightAns = { subj: Int -> mnh.getNoteFromInterval(subj, option) }
        val getNewId = { getRandomId() }
        val generateTakenSet: (Int) -> MutableSet<Int> = {subj: Int ->
            val set = mutableSetOf<Int>();
            set.add(subj)
            set.add(mnh.getNoteFromInterval(subj, option))
            set.add(subj - 7)
            set.add(subj + 7)
            set}
        val getTextById = mnh::getNoteName
        val getOptTxt = { mnh.getIntervalName(option) }

        return FunctionalQM(getNewSubj,
            getSubjTxt,
            getRightAns,
            getNewId,
            getTextById,
            generateTakenSet,
            getOptTxt,
            amountOfAnswrs)
    }

    private fun getChordQM(option: Int): QuestionMaker {
        var chord = setOf<Int>()
        val getNewSubj = { val subj = getRandomId()
            chord = MusicNameHandler.buildChord(subj, toQuaternaryList(option));
            subj
        }
        val getSubjTxt = { subj: Int ->
            MusicNameHandler
                .buildChord(subj, toQuaternaryList(option))
                .shuffled().joinToString(", ") { mnh.getNoteName(it) }
        }
        val getRightAns = {subj: Int -> subj}
        val getNewId = {chord.random()}
        val generateTakenSet = {subj: Int -> mutableSetOf(subj)}
        val getTextById = mnh::getNoteName
        val getOptTxt = {"SOON"}

        return FunctionalQM(getNewSubj,
            getSubjTxt,
            getRightAns,
            getNewId,
            getTextById,
            generateTakenSet,
            getOptTxt,
            amountOfAnswrs)

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



    private class FunctionalQM (private val getNewSubj: () -> Int,
                                private val getSubjTxt: (Int) -> String,
                                private val getRightAns: (Int) -> Int,
                                private val getNewId: () -> Int,
                                private val getTextById: (Int) -> String,
                                private val generateTakenSet: (Int) -> MutableSet<Int>,
                                private val getOptTxt: () -> String,
                                private val amountOfAnswrs: Int,
                                ): QuestionMaker {
        private var subj = 0
        private var rightButton = 0
        private var rightAns = 0


        override fun feedArguments(prevSubj: Int, rightButton: Int) {
            var subj = prevSubj
            while (subj == prevSubj) {
                subj = getNewSubject()
            }
            this.subj = subj
            this.rightButton = rightButton
            rightAns =  getRightAnswer()
        }

        override fun getSubjectText(): String {
            return getSubjTxt(subj)
        }

        override fun getOptionText(): String {
            return getOptTxt()
        }

        override fun getSubject(): Int {
            return subj
        }

        override fun getAmountOfAnswers(): Int {
            return  amountOfAnswrs
        }

        override fun getButtonTexts(): List<String> {
            val takenSet = getTakenSet()
            val list = mutableListOf<Int>()
            for (i in 0 until amountOfAnswrs) {
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
            return list.map { getTextById(it) }
        }

        private fun getNewSubject(): Int {
            return getNewSubj()
        }

        private fun getRightAnswer(): Int {
            return getRightAns(subj)
        }

        private fun getTakenSet(): MutableSet<Int> {
            return generateTakenSet(subj)
        }
    }
}