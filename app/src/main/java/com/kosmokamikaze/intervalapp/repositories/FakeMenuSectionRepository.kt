package com.kosmokamikaze.intervalapp.repositories

import androidx.lifecycle.MutableLiveData
import com.kosmokamikaze.intervalapp.models.MenuSection

class FakeMenuSectionRepository {
    companion object Instance {
        val instance = FakeMenuSectionRepository()
    }

    private val dataSet = mutableListOf<MenuSection>()

    // Imitation of getting data from database

    fun getData(): MutableLiveData<List<MenuSection>> {
        setDataSet()

        val data = MutableLiveData<List<MenuSection>>()

        data.value = dataSet

        return data
    }

    private fun setDataSet() {
        dataSet.add(MenuSection("КВИНТЫ", 0, 0, 1, 5, 4))
        dataSet.add(MenuSection("КВАРТЫ", 0, 0, -1, 5, 4))
        dataSet.add(MenuSection("ИНТЕРВАЛ", 0, 1, 0, 5, 4))
        dataSet.add(MenuSection("АККОРД", 0, 2, 10, 3, 3))
        dataSet.add(MenuSection("МАЖОРНЫЙ", 0, 4, 2726, 5, 9))
    }
}