package com.kosmokamikaze.intervalapp.musical

import android.content.res.Resources
import com.kosmokamikaze.intervalapp.R

data class MusicalNames(
    val noteNames: Array<String>,
    val intervalNames: Array<String>,
    val intervalNamesAccusative: Array<String>,
    val chordTypeNames: Array<String>,
    val shortIntervalNames: Array<String>,
    val shortChordNames: Array<String>,
    val scaleNames: Array<String>
) {
    constructor(resources: Resources, doSystem: Boolean = false) : this(
        if (doSystem) resources.getStringArray(R.array.note_names_do)
        else resources.getStringArray(R.array.note_names),
        resources.getStringArray(R.array.interval_names),
        resources.getStringArray(R.array.interval_names_accusative),
        resources.getStringArray(R.array.chord_types),
        resources.getStringArray(R.array.interval_names_short),
        resources.getStringArray(R.array.short_chord_types),
        resources.getStringArray(R.array.scale_names)
    )

    companion object {
        const val DO_SYSTEM = "doSystem"
    }
}