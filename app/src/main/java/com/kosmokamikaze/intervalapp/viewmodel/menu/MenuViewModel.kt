package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.App
import com.kosmokamikaze.intervalapp.model.data.DataBaseFiller
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application, private val typeGroup: TypeGroups) : ViewModel() {

    var quizGroup: LiveData<List<QuizData>>
    private val repository: QuizRepository


    init {
        repository = QuizRepository(application.applicationContext)

        val sharedPreferences = application.getSharedPreferences(App.QUIZ_DATA, Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean(App.DATA_BASE_CREATED, false)) {
            buildDataBase()
            with(sharedPreferences.edit()) {
                putBoolean(App.DATA_BASE_CREATED, true)
                apply()
            }
        }

        quizGroup = repository.readData(typeGroup)
    }

    private fun addQuiz(quizData: QuizData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuiz(quizData)
        }
    }

    private fun buildDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseFiller.addInitialValues(repository)
        }
    }

    fun updateHighScore(id: Int, highScore: Int): Boolean {
        var prevHighScore = 0
        for (data in quizGroup.value!!) {
            if (data.id == id) {
                prevHighScore = data.highScore
                break
            }
        }
        if (prevHighScore >= highScore) {
            return false
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHighScore(id, highScore)
        }
        quizGroup = repository.readData(typeGroup)
        return true
    }
}