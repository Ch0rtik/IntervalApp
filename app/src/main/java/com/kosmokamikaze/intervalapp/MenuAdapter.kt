package com.kosmokamikaze.intervalapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kosmokamikaze.intervalapp.databinding.MenuItemBinding
import com.kosmokamikaze.intervalapp.models.MenuSection

class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private var items: List<MenuSection> = ArrayList()

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

    fun submitList(list: List<MenuSection>) {
        items = list
    }

    class MenuViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MenuItemBinding.bind(item)

        private lateinit var menuSection: MenuSection
        fun bind(menuSection: MenuSection) = with(binding) {
            this@MenuViewHolder.menuSection = menuSection
            title.text = menuSection.title
            highScore.text = menuSection.highScore.toString()
        }

        init {
            item.setOnClickListener {
                val intent = Intent(item.context, QuizActivity::class.java)
                intent.putExtra("type", menuSection.type)
                intent.putExtra("option", menuSection.option)
                intent.putExtra("range", menuSection.range)
                intent.putExtra("amountOfButtons", menuSection.amountOfButtons)
                item.context.startActivity(intent)
            }
        }
    }
}