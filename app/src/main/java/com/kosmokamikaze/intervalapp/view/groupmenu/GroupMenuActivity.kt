package com.kosmokamikaze.intervalapp.view.groupmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kosmokamikaze.intervalapp.databinding.ActivityGroupMenuBinding
import com.kosmokamikaze.intervalapp.model.quiz.TypeGroups
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
        binding.intervalsButton.setOnClickListener { startMenuActivity(TypeGroups.INTERVAL_GROUP) }

        binding.chordsButton.setOnClickListener { startMenuActivity(TypeGroups.CHORD_GROUP) }

        binding.scalesButton.setOnClickListener { startMenuActivity(TypeGroups.SCALE_GROUP) }
    }

    private fun startMenuActivity(typeGroup: TypeGroups) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.TYPE_GROUP, typeGroup)
        startActivity(intent)
    }
}