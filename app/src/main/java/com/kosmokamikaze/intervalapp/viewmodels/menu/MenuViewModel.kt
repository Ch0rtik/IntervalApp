package com.kosmokamikaze.intervalapp.viewmodels.menu

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.data.DataBaseFiller
import com.kosmokamikaze.intervalapp.data.QuizDatabase
import com.kosmokamikaze.intervalapp.data.QuizRepository
import com.kosmokamikaze.intervalapp.data.QuizDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application): ViewModel() {

    var allData: LiveData<List<QuizDataModel>>
    private val repository: QuizRepository


    init {
        val quizDao = QuizDatabase.getDatabase(application).quizDao()
        repository = QuizRepository(quizDao)

        val sharedPreferences = application.getSharedPreferences("quizData", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("dataBaseCreated", false)) {
            buildDataBase()
            with(sharedPreferences.edit()) {
                putBoolean("dataBaseCreated", true)
                apply()
            }
        }

        allData = repository.readAllData()
    }

    private fun addQuiz(quiz: QuizDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuiz(quiz)
        }
    }

    private fun buildDataBase() {
        for (quiz in DataBaseFiller.getInitialValues()) {
            addQuiz(quiz)
        }
    }

    fun updateHighScore(id: Int, highScore: Int) {
        val prevHighScore = allData.value!![id - 1].highScore
        if (prevHighScore >= highScore) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHighScore(id, highScore)
        }
        allData = repository.readAllData()
    }
}