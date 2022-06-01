package com.kosmokamikaze.intervalapp.view.quiz


import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kosmokamikaze.intervalapp.R

class AnswerButton(private val button: Button, private val layout: LinearLayout) {

    private val colorDark = ContextCompat.getColor(button.context, R.color.colorPrimary)
    private val colorLight = ContextCompat.getColor(button.context, android.R.color.background_light)


    fun setText(text: String) {
        button.text = text
    }

    fun press() {
        button.setTextColor(colorLight)
        button.setBackgroundColor(colorDark)
        layout.setBackgroundColor(colorLight)
    }

    fun unpress() {
        button.setTextColor(colorDark)
        button.setBackgroundColor(colorLight)
        layout.setBackgroundColor(colorDark)
    }

    fun makeInvisible() {
        button.isClickable = false
        button.visibility = View.INVISIBLE
        layout.visibility = View.INVISIBLE
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        button.setOnClickListener(listener)
    }
}