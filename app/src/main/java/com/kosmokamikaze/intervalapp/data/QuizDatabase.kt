package com.kosmokamikaze.intervalapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kosmokamikaze.intervalapp.model.QuizDataModel

@Database(entities = [QuizDataModel::class], version = 1, exportSchema = false)
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
                    "quiz_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}