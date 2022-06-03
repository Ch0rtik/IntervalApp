package com.kosmokamikaze.intervalapp.model.musical

import android.content.res.Resources
import com.kosmokamikaze.intervalapp.R

data class MusicalNames(
    val noteNames: Array<String>,
    val intervalNames: Array<String>,
    val intervalNamesAccusative: Array<String>,
    val chordTypeNames: Array<String>,
    val shortIntervalNames: Array<String>,
    val scaleNames: Array<String>,
    val interval: String
) {
    constructor(resources: Resources, doSystem: Boolean = false) : this(
        if (doSystem) resources.getStringArray(R.array.note_names_do)
        else resources.getStringArray(R.array.note_names),
        resources.getStringArray(R.array.interval_names),
        resources.getStringArray(R.array.interval_names_accusative),
        resources.getStringArray(R.array.chord_types),
        resources.getStringArray(R.array.interval_names_short),
        resources.getStringArray(R.array.scale_names),
        resources.getString(R.string.interval)
    )

    companion object {
        const val DO_SYSTEM = "doSystem"
    }
}