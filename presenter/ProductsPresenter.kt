package com.atl.ayan.presenter

import com.atl.ayan.model.entity.Product

interface ProductsPresenter {
    fun requestData(type: String, subType: String)
    fun onAddButtonClick(product: Product)
}