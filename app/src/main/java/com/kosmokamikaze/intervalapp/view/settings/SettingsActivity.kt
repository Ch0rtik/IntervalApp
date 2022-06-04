package com.kosmokamikaze.intervalapp.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.databinding.ActivitySettingsBinding
import com.kosmokamikaze.intervalapp.viewmodel.factory.ViewModelFactory
import com.kosmokamikaze.intervalapp.viewmodel.settings.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                this.applicationContext,
                intent.extras,
                ViewModelFactory.ViewModelType.SETTINGS
            )
        )[SettingsViewModel::class.java]

        setUpButtons()


    }

    private fun setUpButtons() {
        viewModel.doSystemData.observe(this) {
            resources.apply {
                binding.doButton.text =
                    if (it) getString(R.string.c_d_e) else getString(R.string.do_re_mi)
            }
        }

        binding.doButton.setOnClickListener { viewModel.changeNoteSystem() }

        binding.resetButton.setOnClickListener {
            viewModel.resetHighScores()
            Toast.makeText(this, resources.getString(R.string.high_score_reset), Toast.LENGTH_SHORT).show()
        }
    }
}