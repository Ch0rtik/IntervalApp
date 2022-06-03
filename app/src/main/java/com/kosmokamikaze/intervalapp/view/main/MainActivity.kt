package com.kosmokamikaze.intervalapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.databinding.ActivityMainBinding
import com.kosmokamikaze.intervalapp.view.groupmenu.GroupMenuActivity
import com.kosmokamikaze.intervalapp.view.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainImage.setImageResource(R.drawable.main_image)
        binding.quizButton.setOnClickListener {
            val intent = Intent(this, GroupMenuActivity::class.java)
            startActivity(intent)
        }
        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val SHARED_PREFS = "sharedPrefs"
    }
}