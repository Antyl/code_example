package com.atl.ayan.ui.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.atl.ayan.model.entity.OrderHistory

class HistoryDiff : DiffUtil.ItemCallback<OrderHistory>() {

    override fun areItemsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
        return oldItem.orderNumber == newItem.orderNumber
    }

    override fun areContentsTheSame(oldItem: OrderHistory, newItem: OrderHistory): Boolean {
        return oldItem.products == newItem.products
    }
}