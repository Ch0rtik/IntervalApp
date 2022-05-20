package com.kosmokamikaze.intervalapp.viewmodels

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosmokamikaze.intervalapp.MenuActivity
import com.kosmokamikaze.intervalapp.QuizActivity
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.quiz.Quiz

class QuizViewModel(private val colorDark: Int,
                    private val colorLight: Int,
                    extras: Bundle,
                    mth: MusicTheoryHandler): ViewModel() {

    private var possibleButtons = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    private var type = 0
    private var option = 0
    private var range = 0
    private var amountOfButtons = 0

    private val quiz: Quiz
    private val chosenButtons = mutableSetOf<Int>()

    private val mutAnsButtonsLive: Array<MutableLiveData<AnsButtonData>> =
        Array(9) { MutableLiveData() }
    private val mutSubmitButtonLive: MutableLiveData<SubmitButtonData> = MutableLiveData()
    private val mutOptionLive = MutableLiveData<String>()
    private val mutSubjectLive = MutableLiveData<String>()
    private val mutFunctionLive = MutableLiveData<(QuizActivity) -> Unit>()

    val ansButtonsLive: Array<MutableLiveData<AnsButtonData>> = mutAnsButtonsLive
    val submitButtonLive: LiveData<SubmitButtonData> = mutSubmitButtonLive
    val optionLive: LiveData<String> = mutOptionLive
    val subjectLive: LiveData<String> = mutSubjectLive
    val functionLive: LiveData<(QuizActivity) -> Unit> = mutFunctionLive

    init {
        extras.apply {
            type = getInt("type")
            option = getInt("option")
            range = getInt("range")
            amountOfButtons = getInt("amountOfButtons")
        }

        quiz = Quiz(type, option, amountOfButtons, range, mth)

        for (ansButtonLive: MutableLiveData<AnsButtonData> in mutAnsButtonsLive) {
            ansButtonLive.value = AnsButtonData()
        }
        mutSubmitButtonLive.value = SubmitButtonData()

        setUpButtons()

        setUpNewQuestion()
    }

    private fun setUpButtons() {
        if (amountOfButtons == 4) possibleButtons = listOf(0, 2, 6, 8)
        if (amountOfButtons == 3) possibleButtons = listOf(0, 2, 6)

        for (i in mutAnsButtonsLive.indices) {
            if (!possibleButtons.contains(i)) {
                mutAnsButtonsLive[i].value!!.makeInvisible()
            }
        }
    }

    fun chooseAnswer(id: Int) {
        val index = possibleButtons.indexOf(id)
        if (!chosenButtons.contains(index)) {
            if (chosenButtons.size < quiz.amountOfAnswers) {
                chosenButtons.add(index)
                mutAnsButtonsLive[id].value!!.click()
                if (chosenButtons.size == quiz.amountOfAnswers) {
                    mutSubmitButtonLive.value!!.makeVisible()
                }
            }
        } else {
            chosenButtons.remove(index)
            mutAnsButtonsLive[id].value!!.click()
            mutSubmitButtonLive.value!!.makeInvisible()
        }

        mutAnsButtonsLive[id].value = mutAnsButtonsLive[id].value
        mutSubmitButtonLive.value = mutSubmitButtonLive.value
    }

    fun submitAnswer() {
        val result = quiz.submitAnswer(chosenButtons)
        if (result == null) {
            setUpNewQuestion()
            mutFunctionLive.value = { _: QuizActivity ->  }
        } else {
            mutFunctionLive.value = { activity: QuizActivity ->
                activity.startActivity(Intent(activity, MenuActivity::class.java))
            }
        }
    }

    private fun setUpNewQuestion() {
        chosenButtons.clear()
        quiz.askNewQuestion()

        val question = quiz.currentQuestion
        for ((j, i) in possibleButtons.withIndex()) {
            mutAnsButtonsLive[i].value!!.text = question.buttonTexts[j]
            mutAnsButtonsLive[i].value!!.reset()
        }

        mutSubmitButtonLive.value!!.makeInvisible()

        mutOptionLive.value = question.optionText
        mutSubjectLive.value = question.subjectText

        for (ansButtonLive in mutAnsButtonsLive) {
            ansButtonLive.value = ansButtonLive.value
        }
        mutSubmitButtonLive.value = mutSubmitButtonLive.value
    }

    inner class AnsButtonData: BaseObservable() {

        private var isPressed = false

        var text: String = ""

        var textColor: Int = colorDark
        var buttonColor: Int = colorLight
        var bgColor: Int = colorDark

        var visibility = View.VISIBLE
        var clickable = true

        fun click() {
            if (isPressed) unpress() else press()
            isPressed = !isPressed
        }

        fun reset() {
            unpress()
            isPressed = false
        }


        private fun press() {
            textColor = colorLight
            buttonColor = colorDark
            bgColor = colorLight

        }

        private fun unpress() {
            textColor = colorDark
            buttonColor = colorLight
            bgColor = colorDark

        }

        fun makeInvisible()  {
            visibility = View.INVISIBLE
            clickable = false
        }
    }

    inner class SubmitButtonData : BaseObservable() {
        var visibility = View.VISIBLE
        var clickable = true

        fun makeInvisible()  {
            visibility = View.INVISIBLE
            clickable = false
            notifyChange()
        }

        fun makeVisible()  {
            visibility = View.VISIBLE
            clickable = true
            notifyChange()
        }
    }

}