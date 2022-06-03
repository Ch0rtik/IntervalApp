package com.kosmokamikaze.intervalapp.view.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.databinding.ActivitySettingsBinding
import com.kosmokamikaze.intervalapp.musical.MusicalNames
import com.kosmokamikaze.intervalapp.repository.QuizRepository
import com.kosmokamikaze.intervalapp.view.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPrefs: SharedPreferences
    private var doSystem = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resetButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                QuizRepository(applicationContext).resetHighScores()
                Toast.makeText(this@SettingsActivity, resources.getString(R.string.records_reset), Toast.LENGTH_SHORT).show()
            }
        }

        sharedPrefs = getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)
        doSystem = sharedPrefs.getBoolean(MusicalNames.DO_SYSTEM, false)

        setUpDoButton()
        binding.doButton.setOnClickListener {
            doSystem = !doSystem
            with(sharedPrefs.edit()) {
                putBoolean(MusicalNames.DO_SYSTEM, doSystem)
                apply()
            }
            setUpDoButton()
        }
    }

    private fun setUpDoButton() {
        resources.apply {
            binding.doButton.text = if (doSystem) getString(R.string.c_d_e) else getString(R.string.do_re_mi)
        }
    }
}