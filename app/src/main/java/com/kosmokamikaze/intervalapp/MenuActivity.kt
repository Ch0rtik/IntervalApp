package com.kosmokamikaze.intervalapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosmokamikaze.intervalapp.adapters.MenuAdapter
import com.kosmokamikaze.intervalapp.adapters.TopSpacingDecoration
import com.kosmokamikaze.intervalapp.data.QuizDataModel
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

        setData()
    }


    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent = it.data!!
            data.apply {
                viewModel.updateHighScore(getIntExtra(QuizDataModel.ID, 1), getIntExtra(QuizDataModel.HIGH_SCORE, 35))
            }
        }
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@MenuActivity)
        val decoration = TopSpacingDecoration()
        addItemDecoration(decoration)
        adapter = menuAdapter
    }

    private fun setData() {
        viewModel.allData.observe(this) {
            menuAdapter.setData(it)
        }
    }
}