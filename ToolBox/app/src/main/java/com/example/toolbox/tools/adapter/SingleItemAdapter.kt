package com.example.toolbox.tools.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

class StaticViewAdapter<BindingType : ViewBinding>(
    private val createBinding: (parent: ViewGroup) -> BindingType,
) : ListAdapter<Any, StaticViewHolder<BindingType>>(StaticItemDiffUtil()) {

    init {
        submitList(listOf(Unit))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StaticViewHolder<BindingType> {
        return StaticViewHolder(createBinding(parent))
    }

    override fun onBindViewHolder(holder: StaticViewHolder<BindingType>, position: Int) {
        // No need to bind data for static item
    }

}

class StaticViewHolder<BindingType : ViewBinding>(
    val binding: BindingType
) : ViewHolder(binding.root)

class StaticItemDiffUtil : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any) = true
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = true
}
