package com.kosmokamikaze.intervalapp.viewmodel.settings

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosmokamikaze.intervalapp.model.musical.MusicalNames
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import com.kosmokamikaze.intervalapp.view.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(context: Context): ViewModel() {
    private val repository: QuizRepository
    private val mutDoSystemData: MutableLiveData<Boolean>
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)

    val doSystemData: LiveData<Boolean> get() = mutDoSystemData


    init {
        repository = QuizRepository(context)
        mutDoSystemData = MutableLiveData()
        mutDoSystemData.value = sharedPrefs.getBoolean(MusicalNames.DO_SYSTEM, false)
    }

    fun resetHighScores() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.resetHighScores()
        }
    }

    fun changeNoteSystem() {
        mutDoSystemData.value = !mutDoSystemData.value!!
        with(sharedPrefs.edit()) {
            putBoolean(MusicalNames.DO_SYSTEM, mutDoSystemData.value!!)
            apply()
        }
    }
}