package com.kosmokamikaze.intervalapp.viewmodel.factory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.musical.MusicalNames
import com.kosmokamikaze.intervalapp.view.main.MainActivity
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
            ViewModelType.QUIZ -> QuizViewModel(
                MusicalNames(context.resources, context
                    .getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)
                    .getBoolean(MusicalNames.DO_SYSTEM, false)),
                extras
            ) as T
        }
    }
}