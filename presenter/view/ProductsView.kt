package com.atl.ayan.presenter.view

import com.atl.ayan.model.entity.Product

interface ProductsView {
    fun setData(products: MutableList<Product>)
    fun setEmpty()
    fun setCountProducts(count: Double)
}