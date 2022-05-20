package com.kosmokamikaze.intervalapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosmokamikaze.intervalapp.models.MenuSection

class MenuActivityViewModel: ViewModel() {
    private lateinit var mMenuSections: MutableLiveData<List<MenuSection>>

    fun getMenuSections(): LiveData<List<MenuSection>> {
        return mMenuSections
    }
}