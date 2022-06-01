package com.kosmokamikaze.intervalapp.view.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosmokamikaze.intervalapp.adapters.MenuAdapter
import com.kosmokamikaze.intervalapp.adapters.TopSpacingDecoration
import com.kosmokamikaze.intervalapp.model.QuizDataModel
import com.kosmokamikaze.intervalapp.databinding.ActivityMenuBinding
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModel
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModelFactory


class MenuActivity : AppCompatActivity() {
    companion object {
        const val TYPE_GROUP = "typeGroup"
    }
    private lateinit var binding: ActivityMenuBinding

    private val menuAdapter = MenuAdapter()

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MenuViewModelFactory(this.application, intent.getIntExtra(
            TYPE_GROUP, 0)))[MenuViewModel::class.java]


        initRecyclerView()

        setData()
    }


    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent = it.data!!
            var newRecordSet: Boolean
            data.apply {
                newRecordSet = viewModel.updateHighScore(getIntExtra(QuizDataModel.ID, 1), getIntExtra(
                    QuizDataModel.HIGH_SCORE, 35))
            }
            if (newRecordSet) Toast.makeText(this, "!!! Новый рекорд !!!", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}