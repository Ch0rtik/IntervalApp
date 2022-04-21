package com.kosmokamikaze.intervalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class QuizActivity : AppCompatActivity() {
    lateinit var ansBttns: Array<Button>
    lateinit var ansLyts: Array<LinearLayout>
    lateinit var subjText: TextView
    lateinit var optText: TextView
    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setUpViews()
        quiz = Quiz(true, this)
        setOnClickListeners()
        quiz.askNewQuestion()
    }

    private fun setOnClickListeners() {
        for (i in ansBttns.indices) {
            ansBttns[i].setOnClickListener {
                val result = quiz.giveAnswer(i)
                if (result == null) {
                    quiz.askNewQuestion()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
             }
        }
    }

    private fun setUpViews() {
        ansBttns = arrayOf(
            findViewById(R.id.answerButton0),
            findViewById(R.id.answerButton1),
            findViewById(R.id.answerButton2),
            findViewById(R.id.answerButton3),
            findViewById(R.id.answerButton4),
            findViewById(R.id.answerButton5),
            findViewById(R.id.answerButton6),
            findViewById(R.id.answerButton7),
            findViewById(R.id.answerButton8),)

        ansLyts = arrayOf(
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
    }
}