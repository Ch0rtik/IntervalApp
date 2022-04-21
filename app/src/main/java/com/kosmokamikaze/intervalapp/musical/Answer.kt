package com.kosmokamikaze.intervalapp.musical

import android.widget.Button

class Answer(button: Button, musical: Musical) {
    init {
        button.text = musical.getNameFromRes()
    }
}