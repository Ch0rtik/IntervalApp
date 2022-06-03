package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.App
import com.kosmokamikaze.intervalapp.model.data.DataBaseFiller
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import com.kosmokamikaze.intervalapp.view.menu.MenuActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(context: Context, extras: Bundle) : ViewModel() {
    var quizGroup: LiveData<List<QuizData>>
    private val repository: QuizRepository
    private val typeGroup = extras.getSerializable(QuizData.TYPE_GROUP) as TypeGroups

    init {
        repository = QuizRepository(context.applicationContext)

        quizGroup = repository.readData(typeGroup)
    }

    private fun addQuiz(quizData: QuizData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuiz(quizData)
        }
    }

    private fun updateHighScoreData(id: Int, highScore: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHighScore(id, highScore)
        }
    }

    fun updateHighScore(id: Int, highScore: Int): Boolean {
        for (data in quizGroup.value!!) {
            if (data.id == id) {
                if (data.highScore >= highScore) return false
                updateHighScoreData(id, highScore)
                data.highScore = highScore
                break
            }
        }
        return true
    }
}