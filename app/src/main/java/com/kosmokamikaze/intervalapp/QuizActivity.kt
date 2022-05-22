package com.kosmokamikaze.intervalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.viewmodels.quiz.QuizViewModel
import com.kosmokamikaze.intervalapp.viewmodels.quiz.QuizViewModelFactory

class QuizActivity : AppCompatActivity() {
    private lateinit var ansButtons: Array<Button>
    private lateinit var ansLayouts: Array<LinearLayout>
    private lateinit var subjText: TextView
    private lateinit var optText: TextView
    private lateinit var submitButton: Button
    private lateinit var submitLayout: LinearLayout

    private lateinit var viewModel: QuizViewModel

    private lateinit var returnToMenu: (QuizActivity) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        viewModel = ViewModelProvider(this, QuizViewModelFactory(this, intent.extras!!))
            .get(QuizViewModel::class.java)

        setUpViews()
        setOnClickListeners()

        for (i in 0..8) {
            viewModel.ansButtonsLive[i].observe(this) { buttonData ->
                buttonData.apply {
                    ansButtons[i].text = text
                    ansButtons[i].setTextColor(textColor)
                    ansButtons[i].setBackgroundColor(buttonColor)
                    ansLayouts[i].setBackgroundColor(bgColor)

                    ansButtons[i].isClickable = clickable
                    ansButtons[i].visibility = visibility
                    ansLayouts[i].visibility = visibility
                }
            }
        }

        viewModel.submitButtonLive.observe(this) { submitData ->
            submitData.apply {
                submitButton.visibility = visibility
                submitButton.isClickable = clickable
                submitLayout.visibility = visibility
            }
        }

        viewModel.subjectLive.observe(this) {
            subjText.text = it
        }

        viewModel.optionLive.observe(this) {
            optText.text = it
        }

        viewModel.returnToMenuLive.observe(this) {
            returnToMenu = it
        }
    }

    private fun setOnClickListeners() {
        for (i in ansButtons.indices) {
            ansButtons[i].setOnClickListener {
                viewModel.chooseAnswer(i)
             }
        }

        submitButton.setOnClickListener {
            viewModel.submitAnswer()
            returnToMenu(this)
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
            findViewById(R.id.answerButton8))

        ansLayouts = arrayOf(
            findViewById(R.id.answerLayout0),
            findViewById(R.id.answerLayout1),
            findViewById(R.id.answerLayout2),
            findViewById(R.id.answerLayout3),
            findViewById(R.id.answerLayout4),
            findViewById(R.id.answerLayout5),
            findViewById(R.id.answerLayout6),
            findViewById(R.id.answerLayout7),
            findViewById(R.id.answerLayout8))

        subjText = findViewById(R.id.subjectText)
        optText = findViewById(R.id.optionText)

        submitButton = findViewById(R.id.submitButton)
        submitLayout = findViewById(R.id.submitLayout)
    }
}