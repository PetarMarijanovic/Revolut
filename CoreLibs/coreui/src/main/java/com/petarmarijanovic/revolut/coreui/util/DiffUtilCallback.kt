package com.petarmarijanovic.revolut.coreui.util

import androidx.recyclerview.widget.DiffUtil

open class DiffUtilCallback<T : DiffUtilViewModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}

class PayloadDiffUtilCallback<T : DiffUtilViewModel> : DiffUtilCallback<T>() {

    override fun getChangePayload(oldItem: T, newItem: T): Any? = Pair(oldItem, newItem)
}

abstract class DiffUtilViewModel(open val id: Any? = null)
