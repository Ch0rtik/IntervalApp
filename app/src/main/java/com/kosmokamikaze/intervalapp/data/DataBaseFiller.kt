package com.kosmokamikaze.intervalapp.data

class DataBaseFiller {
    companion object {
        fun getInitialValues(): List<QuizDataModel> {
            val dataSet = mutableListOf<QuizDataModel>()
            dataSet.add(QuizDataModel(1,"КВИНТЫ", 0, 0, 1, 5, 4))
            dataSet.add(QuizDataModel(2,"КВАРТЫ", 0, 0, -1, 5, 4))
            dataSet.add(QuizDataModel(3,"ИНТЕРВАЛ", 0, 1, 0, 5, 4))
            dataSet.add(QuizDataModel(4,"АККОРД", 0, 2, 10, 3, 3))
            dataSet.add(QuizDataModel(5,"МАЖОРНЫЙ", 0, 4, 2726, 5, 9))

            return dataSet
        }
    }
}