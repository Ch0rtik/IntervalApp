package com.kosmokamikaze.intervalapp.view.groupmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kosmokamikaze.intervalapp.databinding.ActivityGroupMenuBinding
import com.kosmokamikaze.intervalapp.view.menu.MenuActivity

class GroupMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.intervalsButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.TYPE_GROUP, 0)
            startActivity(intent)
        }

        binding.chordsButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.TYPE_GROUP, 1)
            startActivity(intent)
        }

        binding.scalesButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.TYPE_GROUP, 2)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}