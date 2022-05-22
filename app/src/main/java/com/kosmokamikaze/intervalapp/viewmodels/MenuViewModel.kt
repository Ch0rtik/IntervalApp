package com.kosmokamikaze.intervalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosmokamikaze.intervalapp.models.MenuSection
import com.kosmokamikaze.intervalapp.repositories.FakeMenuSectionRepository

class MenuViewModel: ViewModel() {
    private val mutMenuSections: MutableLiveData<List<MenuSection>> = FakeMenuSectionRepository.instance.getData()

    val menuSections: LiveData<List<MenuSection>> = mutMenuSections
}