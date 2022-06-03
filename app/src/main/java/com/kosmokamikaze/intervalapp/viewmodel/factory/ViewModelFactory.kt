package com.kosmokamikaze.intervalapp.viewmodel.factory

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.musical.MusicalNames
import com.kosmokamikaze.intervalapp.view.menu.MenuActivity
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModel
import com.kosmokamikaze.intervalapp.viewmodel.quiz.QuizViewModel

class ViewModelFactory(
    private val application: Application,
    private val extras: Bundle,
    private val viewModelType: ViewModelType
) : ViewModelProvider.Factory {
    enum class ViewModelType {
        MENU, QUIZ
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (viewModelType) {
            ViewModelType.MENU -> createMenuViewModel(
                application,
                extras.getSerializable(MenuActivity.TYPE_GROUP) as TypeGroups
            ) as T
            ViewModelType.QUIZ -> createQuizViewModel(application.applicationContext, extras) as T
        }
    }

    private fun createMenuViewModel(
        application: Application,
        typeGroup: TypeGroups
    ): MenuViewModel {
        return MenuViewModel(application, typeGroup)
    }

    private fun createQuizViewModel(context: Context, extras: Bundle): QuizViewModel {
        return QuizViewModel(extras, MusicTheoryHandler(MusicalNames(context.resources)))
    }
}