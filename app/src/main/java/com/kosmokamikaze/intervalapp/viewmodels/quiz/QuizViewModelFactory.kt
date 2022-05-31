package com.kosmokamikaze.intervalapp.viewmodels.quiz

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler

class QuizViewModelFactory(context: Context, private val extras: Bundle): ViewModelProvider.Factory {
    private val mth = MusicTheoryHandler(context.resources)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(extras, mth) as T
    }
}