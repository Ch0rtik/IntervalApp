package com.kosmokamikaze.intervalapp.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TopSpacingDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = 15
    }
}