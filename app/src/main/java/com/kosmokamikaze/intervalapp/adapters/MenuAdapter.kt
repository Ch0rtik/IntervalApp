package com.kosmokamikaze.intervalapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kosmokamikaze.intervalapp.QuizActivity
import com.kosmokamikaze.intervalapp.R
import com.kosmokamikaze.intervalapp.databinding.MenuItemBinding
import com.kosmokamikaze.intervalapp.data.QuizDataModel

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private var items: List<QuizDataModel> = ArrayList()

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

    fun setData(list: List<QuizDataModel>) {
        items = list
        notifyDataSetChanged()
    }

    class MenuViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MenuItemBinding.bind(item)

        private lateinit var quizDataModel: QuizDataModel
        fun bind(quizDataModel: QuizDataModel) = with(binding) {
            this@MenuViewHolder.quizDataModel = quizDataModel
            title.text = quizDataModel.title
            highScore.text = quizDataModel.highScore.toString()
        }

        init {
            item.setOnClickListener {
                val intent = Intent(item.context, QuizActivity::class.java)
                intent.putExtra("type", quizDataModel.type)
                intent.putExtra("option", quizDataModel.option)
                intent.putExtra("range", quizDataModel.range)
                intent.putExtra("amountOfButtons", quizDataModel.amountOfButtons)
                item.context.startActivity(intent)
            }
        }
    }
}