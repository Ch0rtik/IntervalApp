package com.kosmokamikaze.intervalapp.musical

import android.content.res.Resources
import com.kosmokamikaze.intervalapp.R

data class MusicalNames(val noteNames: Array<String>,
                        val intervalNames: Array<String>,
                        val shortIntervalNames: Array<String>
) {
    constructor(resources: Resources) : this(
        resources.getStringArray(R.array.note_names),
        resources.getStringArray(R.array.interval_names),
        resources.getStringArray(R.array.interval_names_short)
    )
}