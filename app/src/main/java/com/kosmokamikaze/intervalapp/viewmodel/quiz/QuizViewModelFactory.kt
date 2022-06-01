package com.kosmokamikaze.intervalapp.viewmodel.quiz

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import com.kosmokamikaze.intervalapp.musical.MusicalNames

class QuizViewModelFactory(context: Context, private val extras: Bundle): ViewModelProvider.Factory {
    private val mth = MusicTheoryHandler(MusicalNames(context.resources))


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(extras, mth) as T
    }
}