package com.kosmokamikaze.intervalapp.view.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.view.menu.adapter.MenuAdapter
import com.kosmokamikaze.intervalapp.view.menu.adapter.TopSpacingDecoration
import com.kosmokamikaze.intervalapp.databinding.ActivityMenuBinding
import com.kosmokamikaze.intervalapp.model.quiz.QuizData
import com.kosmokamikaze.intervalapp.viewmodel.factory.ViewModelFactory
import com.kosmokamikaze.intervalapp.viewmodel.menu.MenuViewModel


class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    private val menuAdapter = MenuAdapter()

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                this.applicationContext,
                intent.extras!!,
                ViewModelFactory.ViewModelType.MENU
            )
        )[MenuViewModel::class.java]

        initRecyclerView()
        setData()
    }

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent = it.data!!
                data.apply {
                    val newRecordSet = viewModel.updateHighScore(
                        getIntExtra(QuizData.ID, 1),
                        getIntExtra(QuizData.HIGH_SCORE, 0)
                    )
                    if (newRecordSet) Toast.makeText(
                        this@MenuActivity,
                        resources.getString(R.string.new_record),
                        Toast.LENGTH_SHORT
                    ).show()
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
        viewModel.quizGroup.observe(this) {
            menuAdapter.setData(it)
        }
    }
}