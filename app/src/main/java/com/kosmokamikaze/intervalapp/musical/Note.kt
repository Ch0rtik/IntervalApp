package com.kosmokamikaze.intervalapp.musical

import android.content.Context
import com.kosmokamikaze.intervalapp.R

class Note(private val relPos: Int, private val context: Context): MusicalSubject(relPos, context) {

    fun getInterval(other: Note, thisIsLower: Boolean = true): Interval {
        return Interval(this.relPos - other.relPos, context)
    }

    private object Consts {
        const val ZERO_POSITION = 15;
    }
    override fun getNameFromRes(): String {
        return context.resources.getStringArray(R.array.note_names)[relPos + Consts.ZERO_POSITION]
    }

    override fun makeQuestion(musical: Musical): Musical {
        if (musical is Interval) {
            return makeQuestion(musical)
        }
        return TODO()
    }

    private fun makeQuestion(interval: Interval): Musical {
        return Note(relPos + interval.relPos, context)
    }
}