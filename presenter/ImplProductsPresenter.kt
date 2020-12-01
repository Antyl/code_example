package com.atl.ayan.presenter

import com.atl.ayan.model.entity.Product
import com.atl.ayan.model.repository.CartRepository
import com.atl.ayan.model.repository.TypesRepository
import com.atl.ayan.presenter.view.ProductsView

class ImplProductsPresenter(var productsView: ProductsView,
                            var typesRepository: TypesRepository,
                            var cartRepository: CartRepository)
    : ProductsPresenter {

    override fun requestData(type: String, subType: String) {
        val products = typesRepository.getProducts(type, subType)
        if (products.isEmpty())
            productsView.setEmpty()
        else
            productsView.setData(products)
    }

    override fun onAddButtonClick(product: Product) {
        cartRepository.addProduct(product)
        productsView.setCountProducts(cartRepository.getProductCountInCart())
    }
}