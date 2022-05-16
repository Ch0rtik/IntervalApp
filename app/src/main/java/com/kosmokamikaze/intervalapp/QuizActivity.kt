package com.kosmokamikaze.intervalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler

class QuizActivity : AppCompatActivity() {
    private lateinit var ansButtons: Array<Button>
    private lateinit var ansLayouts: Array<LinearLayout>
    private lateinit var subjText: TextView
    private lateinit var optText: TextView
    private lateinit var submitButton: Button
    private lateinit var submitLayout: LinearLayout

    private var possibleButtons = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

    private val chosenButtons = mutableSetOf<Int>()

    ////////////////////////////////
    private val type = 0
    private val option = 1
    private val range = 3
    private val amountOfButtons = 4
    ////////////////////////////////

    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setUpViews()
        val mnh = MusicTheoryHandler(this)
        quiz = Quiz(type, option, amountOfButtons, range, mnh)
        setOnClickListeners()
        setUpNewQuestion()
    }

    private fun setOnClickListeners() {
        for (i in ansButtons.indices) {
            ansButtons[i].setOnClickListener {
                val index = possibleButtons.indexOf(i)
                if (!chosenButtons.contains(index)) {
                    if (chosenButtons.size < quiz.amountOfAnswers) {
                        chosenButtons.add(index)
                        ansButtons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        ansButtons[i].setTextColor(ContextCompat.getColor(this, android.R.color.background_light))
                        ansLayouts[i].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light))
                        if (chosenButtons.size == quiz.amountOfAnswers) {
                            submitButton.visibility = View.VISIBLE
                            submitButton.isClickable = true
                            submitLayout.visibility = View.VISIBLE
                        }
                    }
                } else {
                    chosenButtons.remove(index)
                    ansButtons[i].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light))
                    ansButtons[i].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    ansLayouts[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    if (submitButton.isClickable) {
                        submitButton.visibility = View.INVISIBLE
                        submitButton.isClickable = false
                        submitLayout.visibility = View.INVISIBLE
                    }
                }
             }
        }

        submitButton.setOnClickListener {
            val result = quiz.giveAnswer(chosenButtons)
            if (result == null) {
                setUpNewQuestion()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setUpViews() {
        ansButtons = arrayOf(
            findViewById(R.id.answerButton0),
            findViewById(R.id.answerButton1),
            findViewById(R.id.answerButton2),
            findViewById(R.id.answerButton3),
            findViewById(R.id.answerButton4),
            findViewById(R.id.answerButton5),
            findViewById(R.id.answerButton6),
            findViewById(R.id.answerButton7),
            findViewById(R.id.answerButton8),)

        ansLayouts = arrayOf(
            findViewById(R.id.answerLayout0),
            findViewById(R.id.answerLayout1),
            findViewById(R.id.answerLayout2),
            findViewById(R.id.answerLayout3),
            findViewById(R.id.answerLayout4),
            findViewById(R.id.answerLayout5),
            findViewById(R.id.answerLayout6),
            findViewById(R.id.answerLayout7),
            findViewById(R.id.answerLayout8),)

        subjText = findViewById(R.id.subjectText)
        optText = findViewById(R.id.optionText)

        submitButton = findViewById(R.id.submitButton)
        submitLayout = findViewById(R.id.submitLayout)

        if (amountOfButtons == 4) possibleButtons = listOf(0, 2, 6, 8)

        if (amountOfButtons == 3) possibleButtons = listOf(0, 2, 6)

        for (i in ansButtons.indices) {
            if (!possibleButtons.contains(i)) {
                ansButtons[i].visibility = View.INVISIBLE
                ansButtons[i].isClickable = false
                ansLayouts[i].visibility = View.INVISIBLE
            }
        }

        submitButton.visibility = View.INVISIBLE
        submitButton.isClickable = false
        submitLayout.visibility = View.INVISIBLE
    }

    private fun setUpNewQuestion() {
        quiz.askNewQuestion()
        val question = quiz.currentQuestion
        for ((j, i) in possibleButtons.withIndex()) {
            ansButtons[i].text = question.buttonTexts[j]
            ansButtons[i].setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light))
            ansButtons[i].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            ansLayouts[i].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }

        subjText.text = question.subjectText
        optText.text = question.optionText

        chosenButtons.clear()

        submitButton.visibility = View.INVISIBLE
        submitButton.isClickable = false
        submitLayout.visibility = View.INVISIBLE
    }
}