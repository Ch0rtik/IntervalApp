package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.models.MenuSection

class FakeDataSource {
    companion object {
        fun createDataSet(): List<MenuSection> {
            val list = mutableListOf<MenuSection>()
            list.add(MenuSection("КВИНТЫ", 0, 0, 1, 5, 4))
            list.add(MenuSection("КВАРТЫ", 0, 0, -1, 5, 4))
            list.add(MenuSection("ИНТЕРВАЛ", 0, 1, 0, 5, 4))
            list.add(MenuSection("АККОРД", 0, 2, 10, 3, 3))
            list.add(MenuSection("МАЖОРНЫЙ", 0, 4, 2726, 5, 9))

            return list
        }
    }
}