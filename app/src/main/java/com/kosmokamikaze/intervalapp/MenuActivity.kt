package com.kosmokamikaze.intervalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosmokamikaze.intervalapp.adapters.MenuAdapter
import com.kosmokamikaze.intervalapp.databinding.ActivityMenuBinding
import com.kosmokamikaze.intervalapp.viewmodels.menu.MenuViewModel
import com.kosmokamikaze.intervalapp.viewmodels.menu.MenuViewModelFactory

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    private val menuAdapter = MenuAdapter()

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MenuViewModelFactory(this.application)).get(
            MenuViewModel::class.java)


        initRecyclerView()

        viewModel.readAllData.observe(this) {
            menuAdapter.setData(it)
        }
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@MenuActivity)
        adapter = menuAdapter
    }
}