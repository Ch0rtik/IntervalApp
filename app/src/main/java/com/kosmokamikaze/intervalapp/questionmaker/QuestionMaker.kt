package com.kosmokamikaze.intervalapp.questionmaker

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler

interface QuestionMaker {
    fun feedArguments(prevSubj: Int, rightButton: Int)
    fun getSubjectText(): String
    fun getButtonTexts(): List<String>
    fun getOptionText(): String
    fun getSubject(): Int
    val amountOfAnswers: Int
}

abstract class AbstractQuestionMaker(protected val option: Int,
                                     final override val amountOfAnswers: Int,
                                     private val range: Int,
                                     protected val mnh: MusicNameHandler): QuestionMaker {
    protected var subj = getRandomId()
    private var rightButton = 0
    protected var rightAns = 0


    override fun feedArguments(prevSubj: Int, rightButton: Int) {
        var subj = getRandomId()
        while (subj == prevSubj) {
            subj = getRandomId()
        }
        this.subj = subj
        this.rightButton = rightButton
        rightAns = getRightAnswer()
    }

    override fun getButtonTexts(): List<String> {
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

        return list.map { getTextById(it) }
    }

    override fun getSubject(): Int {
        return subj
    }

    protected fun getRandomId(): Int = (-range..range).random()

    protected abstract fun getRightAnswer(): Int

    protected abstract fun getNewId(): Int

    protected abstract fun getTakenSet(): MutableSet<Int>

    protected abstract fun getTextById(id: Int): String
}

class IntervalQM(option: Int, amountOfAnswers: Int, range: Int,
                 mnh: MusicNameHandler
):
    AbstractQuestionMaker(option, amountOfAnswers, range, mnh) {
    override fun getSubjectText(): String {
        return mnh.getNoteName(subj)
    }

    override fun getOptionText(): String {
        return mnh.getIntervalName(option)
    }


    override fun getRightAnswer(): Int {
        return mnh.getNoteFromInterval(subj, option)
    }

    override fun getNewId(): Int {
        return getRandomId()
    }

    override fun getTakenSet(): MutableSet<Int> {
        return mutableSetOf(subj, rightAns, subj+7, subj-7)
    }

    override fun getTextById(id: Int): String {
        return mnh.getNoteName(id)
    }
}

class ChordQM(option: Int, amountOfAnswers: Int, range: Int, mnh: MusicNameHandler) :
    AbstractQuestionMaker(option, amountOfAnswers, range, mnh) {

    private var chord: List<Int> = listOf()

    override fun feedArguments(prevSubj: Int, rightButton: Int) {
        super.feedArguments(prevSubj, rightButton)
        chord = MusicNameHandler.buildChord(subj, toQuaternaryList(option))
    }

    override fun getRightAnswer(): Int {
        return subj
    }

    override fun getNewId(): Int {
        return chord.random()
    }

    override fun getTakenSet(): MutableSet<Int> {
        return mutableSetOf(subj)
    }

    override fun getTextById(id: Int): String {
        return mnh.getNoteName(id)
    }

    override fun getSubjectText(): String {
        return chord.shuffled().joinToString(", ") { mnh.getNoteName(it) }
    }

    override fun getOptionText(): String {
        return "Option"
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