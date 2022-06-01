package com.kosmokamikaze.intervalapp.view.menu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kosmokamikaze.intervalapp.view.menu.MenuActivity
import com.kosmokamikaze.intervalapp.view.quiz.QuizActivity
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.databinding.MenuItemBinding
import com.kosmokamikaze.intervalapp.model.quiz.QuizData

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private var items: List<QuizData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<QuizData>) {
        items = list
        notifyDataSetChanged()
    }

    class MenuViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MenuItemBinding.bind(item)

        private lateinit var quizData: QuizData
        fun bind(quizData: QuizData) = with(binding) {
            this@MenuViewHolder.quizData = quizData
            title.text = quizData.title
            highScore.text = quizData.highScore.toString()
        }

        init {
            item.setOnClickListener {
                val intent = Intent(item.context, QuizActivity::class.java)
                intent.putExtra("quiz", quizData)
                with(item.context as MenuActivity) {
                    resultLauncher.launch(intent)
                }
            }
        }
    }
}