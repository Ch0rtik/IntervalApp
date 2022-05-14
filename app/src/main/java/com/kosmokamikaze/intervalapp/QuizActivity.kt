package com.kosmokamikaze.intervalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.kosmokamikaze.intervalapp.musical.MusicNameHandler
import com.kosmokamikaze.intervalapp.questionmaker.IntervalQM
import com.kosmokamikaze.intervalapp.questionmaker.QMFactory

class QuizActivity : AppCompatActivity() {
    private lateinit var ansBtns: Array<Button>
    private lateinit var ansLyts: Array<LinearLayout>
    private lateinit var subjText: TextView
    private lateinit var optText: TextView
    private var possibleBttns = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

    ////////////////////////////////
    private val type = 1
    private val option = 10
    private val range = 3
    private val amountOfAnswers = 3
    ////////////////////////////////

    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setUpViews()
        val questionMaker = QMFactory(MusicNameHandler(this)).getQuestionMaker(type, option, amountOfAnswers, range)
        quiz = Quiz(questionMaker)
        setOnClickListeners()
        setUpNewQuestion()
    }

    private fun setOnClickListeners() {
        for (i in ansBtns.indices) {
            ansBtns[i].setOnClickListener {
                val result = quiz.giveAnswer(possibleBttns.indexOf(i))
                if (result == null) {
                    setUpNewQuestion()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
             }
        }
    }

    private fun setUpViews() {
        ansBtns = arrayOf(
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

        if (amountOfAnswers == 4) {
            possibleBttns = listOf(0, 2, 6, 8)
            for (i in ansBtns.indices) {
                if (!possibleBttns.contains(i)) {
                    ansBtns[i].visibility = View.INVISIBLE
                    ansBtns[i].isClickable = false
                    ansLyts[i].visibility = View.INVISIBLE
                }
            }
        }

        if (amountOfAnswers == 3) {
            possibleBttns = listOf(0, 2, 6)
            for (i in ansBtns.indices) {
                if (!possibleBttns.contains(i)) {
                    ansBtns[i].visibility = View.INVISIBLE
                    ansBtns[i].isClickable = false
                    ansLyts[i].visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setUpNewQuestion() {
        quiz.askNewQuestion()
        val question = quiz.currentQuest
        for ((j, i) in possibleBttns.withIndex()) {
            ansBtns[i].text = question.buttonTexts[j]
        }
        subjText.text = question.subjText
        optText.text = question.optionText
    }
}