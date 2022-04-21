package com.kosmokamikaze.intervalapp.musical

import android.content.Context

interface Musical {
    fun getNameFromRes(): String
    fun makeQuestion(musical: Musical): Musical
}

abstract class MusicalSubject(relPos: Int, context: Context): Musical {

}