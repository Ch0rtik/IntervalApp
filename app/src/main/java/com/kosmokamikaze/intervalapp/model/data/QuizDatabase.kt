package com.kosmokamikaze.intervalapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kosmokamikaze.intervalapp.model.quiz.QuizData

@Database(entities = [QuizData::class], version = 1, exportSchema = false)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database").createFromAsset("database/quiz_database.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}