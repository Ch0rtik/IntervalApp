package com.kosmokamikaze.intervalapp.viewmodel.menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups

class MenuViewModelFactory(private val application: Application, private val typeGroup: TypeGroups): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MenuViewModel(
            application, typeGroup
        ) as T
    }
}