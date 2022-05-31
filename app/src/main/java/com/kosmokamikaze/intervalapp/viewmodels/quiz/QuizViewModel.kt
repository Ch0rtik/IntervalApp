package com.kosmokamikaze.intervalapp.viewmodels.quiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosmokamikaze.intervalapp.QuizActivity
import com.kosmokamikaze.intervalapp.data.QuizDataModel
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.quiz.Quiz

class QuizViewModel(extras: Bundle,
                    mth: MusicTheoryHandler): ViewModel() {

    private var possibleButtons = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

    private var amountOfButtons = 0

    private val mutButtonPressed: Array<MutableLiveData<Boolean>> =
        Array(9) { MutableLiveData() }
    private val mutButtonVisible: Array<MutableLiveData<Boolean>> =
        Array(9) { MutableLiveData() }
    private val mutButtonTexts: Array<MutableLiveData<String>> =
        Array(9) { MutableLiveData() }


    private val quiz: Quiz
    private val chosenButtons = mutableSetOf<Int>()

    private val mutSubmitButtonLive: MutableLiveData<SubmitButtonData> = MutableLiveData()
    private val mutOptionLive = MutableLiveData<String>()
    private val mutSubjectLive = MutableLiveData<String>()
    private val mutReturnToMenuLive = MutableLiveData<(QuizActivity) -> Unit>()

    val submitButtonLive: LiveData<SubmitButtonData> = mutSubmitButtonLive
    val optionLive: LiveData<String> = mutOptionLive
    val subjectLive: LiveData<String> = mutSubjectLive
    val returnToMenuLive: LiveData<(QuizActivity) -> Unit> = mutReturnToMenuLive

    val buttonPressed = mutButtonPressed
    val buttonVisible = mutButtonVisible
    val buttonTexts = mutButtonTexts

    private var id: Int = 0

    init {
        var type: Int
        var option: Int
        var range: Int
        extras.apply {
            id = getInt(QuizDataModel.ID)
            type = getInt(QuizDataModel.TYPE)
            option = getInt(QuizDataModel.OPTION)
            range = getInt(QuizDataModel.RANGE)
            amountOfButtons = getInt(QuizDataModel.AMOUNT_OF_BUTTONS)
        }

        quiz = Quiz(type, option, amountOfButtons, range, mth)

        for (i in mutButtonPressed.indices) {
            mutButtonPressed[i].value = false
            mutButtonVisible[i].value = true
        }
        mutSubmitButtonLive.value = SubmitButtonData()

        setUpButtons()

        setUpNewQuestion()
    }

    private fun setUpButtons() {
        if (amountOfButtons == 4) possibleButtons = listOf(0, 2, 6, 8)
        if (amountOfButtons == 3) possibleButtons = listOf(0, 2, 7)

        for (i in mutButtonVisible.indices) {
            if (!possibleButtons.contains(i)) {
                mutButtonVisible[i].value = false
            }
        }
    }

    fun chooseAnswer(id: Int) {
        val index = possibleButtons.indexOf(id)

        if (!chosenButtons.contains(index)) {
            if (chosenButtons.size < quiz.amountOfAnswers) {
                chosenButtons.add(index)

                mutButtonPressed[id].value = true
                if (chosenButtons.size == quiz.amountOfAnswers) {
                    mutSubmitButtonLive.value!!.makeVisible()
                }
            }
        } else {
            chosenButtons.remove(index)
            mutButtonPressed[id].value = false
            mutSubmitButtonLive.value!!.makeInvisible()
        }

        mutSubmitButtonLive.value = mutSubmitButtonLive.value
    }

    fun submitAnswer() {
        val result = quiz.submitAnswer(chosenButtons)
        if (result == null) {
            setUpNewQuestion()
            mutReturnToMenuLive.value = { _: QuizActivity ->  }
        } else {
            mutReturnToMenuLive.value = { activity: QuizActivity ->
                val data = Intent()
                data.putExtra(QuizDataModel.ID, id)
                data.putExtra(QuizDataModel.HIGH_SCORE, quiz.score)
                activity.setResult(Activity.RESULT_OK, data)
                activity.finish()
            }
        }
    }

    private fun setUpNewQuestion() {
        chosenButtons.clear()
        quiz.askNewQuestion()

        val question = quiz.currentQuestion
        for ((j, i) in possibleButtons.withIndex()) {

            mutButtonTexts[i].value = question.buttonTexts[j]
            mutButtonPressed[i].value = false
        }

        mutSubmitButtonLive.value!!.makeInvisible()

        mutOptionLive.value = question.optionText
        mutSubjectLive.value = question.subjectText

        mutSubmitButtonLive.value = mutSubmitButtonLive.value
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