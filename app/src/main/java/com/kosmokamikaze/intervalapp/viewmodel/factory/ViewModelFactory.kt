package com.kosmokamikaze.intervalapp.viewmodel.factory

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.musical.MusicalNames
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModel
import com.kosmokamikaze.intervalapp.viewmodel.quiz.QuizViewModel

class ViewModelFactory(
    private val context: Context,
    private val extras: Bundle,
    private val viewModelType: ViewModelType
) : ViewModelProvider.Factory {
    enum class ViewModelType {
        MENU, QUIZ
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (viewModelType) {
            ViewModelType.MENU -> MenuViewModel(
                context,
                extras
            ) as T
            ViewModelType.QUIZ -> QuizViewModel(MusicalNames(context.resources), extras) as T
        }
    }
}