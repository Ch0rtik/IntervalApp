package com.kosmokamikaze.intervalapp

import android.app.Application

class App: Application() {
    companion object {
        const val QUIZ_DATA = "quizData"
        const val DATA_BASE_CREATED = "dataBaseCreated"
    }
    override fun onCreate() {
        super.onCreate()
    }
}