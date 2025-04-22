package com.kosmokamikaze.intervalapp.viewmodel.factory

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.model.musical.MusicalNames
import com.kosmokamikaze.intervalapp.view.main.MainActivity
import com.kosmokamikaze.intervalapp.viewmodel.auth.LoginViewModel
import com.kosmokamikaze.intervalapp.viewmodel.auth.SignupViewModel
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModel
import com.kosmokamikaze.intervalapp.viewmodel.quiz.QuizViewModel
import com.kosmokamikaze.intervalapp.viewmodel.settings.SettingsViewModel

class ViewModelFactory(
    private val context: Context,
    private val extras: Bundle,
    private val type: ViewModelType
) : ViewModelProvider.Factory {
    enum class ViewModelType {
        MENU, QUIZ, LOGIN, SETTINGS, SIGNUP
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (type) {
            ViewModelType.MENU -> MenuViewModel(context, extras) as T
            ViewModelType.QUIZ -> QuizViewModel(MusicalNames(context.resources), extras) as T
            ViewModelType.LOGIN -> LoginViewModel(context) as T
            ViewModelType.SETTINGS -> SettingsViewModel(context) as T
            ViewModelType.SIGNUP -> SignupViewModel(context, extras) as T
        }
    }
}