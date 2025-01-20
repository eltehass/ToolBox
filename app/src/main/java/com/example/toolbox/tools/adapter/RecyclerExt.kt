package com.example.toolbox.tools.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

fun <T : ViewHolder> RecyclerView.setupWithAdapter(
    adapter: RecyclerView.Adapter<T>,
    context: Context,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, orientation, false)
}
