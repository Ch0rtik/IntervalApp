package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(context: Context, extras: Bundle) : ViewModel() {
    var quizGroup: LiveData<List<QuizData>>
    private val repository: QuizRepository
    private val typeGroup = extras.getSerializable(QuizData.TYPE_GROUP) as TypeGroups

    init {
        repository = QuizRepository(context)

        quizGroup = repository.readQuizGroup(typeGroup)
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