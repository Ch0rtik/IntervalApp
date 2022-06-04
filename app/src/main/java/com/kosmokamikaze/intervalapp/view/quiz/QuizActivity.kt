package com.kosmokamikaze.intervalapp.view.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.databinding.ActivityQuizBinding
import com.kosmokamikaze.intervalapp.viewmodel.factory.ViewModelFactory
import com.kosmokamikaze.intervalapp.viewmodel.quiz.QuizViewModel

class QuizActivity : AppCompatActivity() {
    private lateinit var subjText: TextView
    private lateinit var optText: TextView
    private lateinit var submitButton: Button

    private lateinit var answerButtons: Array<AnswerButton>

    private lateinit var viewModel: QuizViewModel

    private lateinit var returnToMenu: (QuizActivity) -> Unit

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this.applicationContext, intent.extras!!, ViewModelFactory.ViewModelType.QUIZ)
        )[QuizViewModel::class.java]

        setUpViews()
        setOnClickListeners()
        setUpObservables()
    }

    private fun setUpObservables() {
        for (i in 0..8) {
            viewModel.buttonsTexts[i].observe(this) {
                answerButtons[i].setText(it)
            }

            viewModel.buttonsVisible[i].observe(this) {
                if (!it) answerButtons[i].makeInvisible()
            }

            viewModel.buttonsPressed[i].observe(this) {
                answerButtons[i].apply {
                    if (it) press() else unpress()
                }
            }
        }

        viewModel.submitButtonLive.observe(this) { submitData ->
            submitData.apply {
                submitButton.visibility = visibility
                submitButton.isClickable = clickable
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
        for (i in answerButtons.indices) {
            answerButtons[i].setOnClickListener {
                viewModel.chooseAnswer(i)
            }
        }

        submitButton.setOnClickListener {
            viewModel.submitAnswer()
            returnToMenu(this)
        }
    }

    private fun setUpViews() {

        answerButtons = arrayOf(
            AnswerButton(binding.answerButton0, binding.answerLayout0),
            AnswerButton(binding.answerButton1, binding.answerLayout1),
            AnswerButton(binding.answerButton2, binding.answerLayout2),
            AnswerButton(binding.answerButton3, binding.answerLayout3),
            AnswerButton(binding.answerButton4, binding.answerLayout4),
            AnswerButton(binding.answerButton5, binding.answerLayout5),
            AnswerButton(binding.answerButton6, binding.answerLayout6),
            AnswerButton(binding.answerButton7, binding.answerLayout7),
            AnswerButton(binding.answerButton8, binding.answerLayout8),
        )

        subjText = binding.subjectText
        optText = binding.optionText

        submitButton = binding.submitButton
    }
}