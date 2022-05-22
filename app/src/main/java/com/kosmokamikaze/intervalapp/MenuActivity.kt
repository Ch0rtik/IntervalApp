package com.kosmokamikaze.intervalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosmokamikaze.intervalapp.adapters.MenuAdapter
import com.kosmokamikaze.intervalapp.databinding.ActivityMenuBinding
import com.kosmokamikaze.intervalapp.viewmodels.MenuViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    private val menuAdapter = MenuAdapter()

    private lateinit var viewModel: MenuViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        viewModel.menuSections.observe(this) {
            menuAdapter.notifyDataSetChanged()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@MenuActivity)
        adapter = menuAdapter
        menuAdapter.submitList(viewModel.menuSections.value!!)
    }
}