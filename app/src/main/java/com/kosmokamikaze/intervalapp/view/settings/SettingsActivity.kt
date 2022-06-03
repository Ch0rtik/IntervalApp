package com.kosmokamikaze.intervalapp.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kosmokamikaze.intervalapp.databinding.ActivitySettingsBinding
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resetButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                QuizRepository(applicationContext).resetHighScores()
            }
        }
    }
}