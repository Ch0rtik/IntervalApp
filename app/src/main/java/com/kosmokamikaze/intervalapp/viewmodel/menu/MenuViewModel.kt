package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.data.DataBaseFiller
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import com.kosmokamikaze.intervalapp.model.QuizDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application, typeGroup: Int): ViewModel() {

    var allData: LiveData<List<QuizDataModel>>
    private val repository: QuizRepository


    init {
        repository = QuizRepository(application.applicationContext)

        val sharedPreferences = application.getSharedPreferences("quizData", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("dataBaseCreated", false)) {
            buildDataBase()
            with(sharedPreferences.edit()) {
                putBoolean("dataBaseCreated", true)
                apply()
            }
        }

        allData = repository.readData(typeGroup)
    }

    private fun addQuiz(quiz: QuizDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuiz(quiz)
        }
    }

    private fun buildDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseFiller.addInitialValues(repository)
        }
    }

    fun updateHighScore(id: Int, highScore: Int): Boolean {
        var prevHighScore = 0
        for (data in allData.value!!) {
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
        allData = repository.readAllData()
        return true
    }
}