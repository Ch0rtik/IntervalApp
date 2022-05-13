package com.kosmokamikaze.intervalapp.questionmaker

import com.kosmokamikaze.intervalapp.musical.MusicNameHandler

class QMFactory(private val mnh: MusicNameHandler) {
    fun getQuestionMaker(type: Int,
                         option: Int): QuestionMaker {
        return when(type) {
            0 -> getIntervalQM(option)
            else -> getIntervalQM(option)
        }
    }

    private fun getIntervalQM(option: Int): QuestionMaker {
        val getRightAns = { subj: Int -> mnh.getNoteFromInterval(subj, option) }
        val getSubjTxt = mnh::getNoteName
        val getBttnTxt = mnh::getNoteName
        val getOptTxt = { mnh.getIntervalName(option) }

        return FunctionalQM(getRightAns, getSubjTxt, getBttnTxt, getOptTxt)
    }



    private class FunctionalQM(private val getRightAns: (Int) -> Int,
                               private val getSubjTxt: (Int) -> String,
                               private val getBttnTxt: (Int) -> String,
                               private val getOptTxt: () -> String): QuestionMaker {
        override fun getRightAnswer(subj: Int): Int {
            return getRightAns(subj)
        }

        override fun getSubjectText(subj: Int): String {
            return getSubjTxt(subj)
        }

        override fun getButtonText(id: Int): String {
            return getBttnTxt(id)
        }

        override fun getOptionText(): String {
            return getOptTxt()
        }
    }
}