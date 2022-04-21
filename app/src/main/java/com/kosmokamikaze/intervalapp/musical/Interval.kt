package com.kosmokamikaze.intervalapp.musical

import android.content.Context
import com.kosmokamikaze.intervalapp.R

class Interval(val relPos: Int, private val context: Context): MusicalSubject(relPos, context) {
    private object Consts {
        const val ZERO_POSITION = 15;
    }
    override fun getNameFromRes(): String {
        return context.resources.getStringArray(R.array.note_names)[relPos + Interval.Consts.ZERO_POSITION]
    }

    override fun makeQuestion(musical: Musical): Musical {
        TODO("Not yet implemented")
    }
}