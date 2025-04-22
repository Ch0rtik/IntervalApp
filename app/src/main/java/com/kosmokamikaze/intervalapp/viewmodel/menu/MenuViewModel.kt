package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(context: Context, extras: Bundle) : ViewModel() {
    private val repository: QuizRepository
    private val typeGroup = extras.getSerializable(QuizData.TYPE_GROUP) as TypeGroups
    
    private val _quizGroup = MutableLiveData<List<QuizData>>()
    val quizGroup: LiveData<List<QuizData>> = _quizGroup

    init {
        repository = QuizRepository(context)
        loadQuizData()
    }

    private fun loadQuizData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.readQuizGroup(typeGroup)
            withContext(Dispatchers.Main) {
                _quizGroup.value = data
            }
        }
    }

    private suspend fun updateHighScoreData(id: Int, highScore: Int) {
        withContext(Dispatchers.IO) {
            repository.updateHighScore(id, highScore)
            val updatedData = repository.readQuizGroup(typeGroup)
            withContext(Dispatchers.Main) {
                _quizGroup.value = updatedData
            }
        }
    }

    fun updateHighScore(id: Int, highScore: Int): Boolean {
        val currentData = _quizGroup.value ?: return false
        for (data in currentData) {
            if (data.id == id) {
                if (data.highScore >= highScore) return false
                viewModelScope.launch {
                    updateHighScoreData(id, highScore)
                    Log.d("UBUNTA", data.highScore.toString())
                    Log.d("UBUNTA", highScore.toString())
                }
                return true
            }
        }
        return false
    }
}