package com.example.toolbox.tools.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

class LazyAdapter<T : Any, BindingType : ViewBinding>(
    mapperForItemsComparison: (T) -> Any,
    private val bindData: (T, BindingType) -> Unit,
    private val createBinding: (parent: ViewGroup) -> BindingType,
) : ListAdapter<T, LazyViewHolder<BindingType>>(LazyDiffUtil<T>(mapperForItemsComparison)) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LazyViewHolder<BindingType> {
        return LazyViewHolder(createBinding(parent))
    }

    override fun onBindViewHolder(holder: LazyViewHolder<BindingType>, position: Int) {
        bindData(getItem(position), holder.binding)
    }

}

class LazyViewHolder<BindingType : ViewBinding>(val binding: BindingType) : ViewHolder(binding.root)

class LazyDiffUtil<T : Any>(
    private val mapperForItemsComparison: (T) -> Any,
    private val areContentsSame: (a: T, b: T) -> Boolean = { a, b -> a == b },
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return mapperForItemsComparison(oldItem) == mapperForItemsComparison(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsSame(oldItem, newItem)
    }
}
