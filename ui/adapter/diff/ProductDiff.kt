package com.atl.ayan.ui.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.atl.ayan.model.entity.Product

class ProductDiff : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.idProduct == newItem.idProduct
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.nameProduct == newItem.nameProduct
    }
}