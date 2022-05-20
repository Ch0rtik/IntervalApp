package com.kosmokamikaze.intervalapp.models

import android.widget.Button

data class MenuSection(val title: String,
                       val highScore: Int,
                       val type: Int,
                       val option: Int,
                       val range: Int,
                       val amountOfButtons: Int)
