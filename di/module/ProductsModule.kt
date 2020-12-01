package com.atl.ayan.di.module

import com.atl.ayan.model.repository.CartRepository
import com.atl.ayan.model.repository.TypesRepository
import com.atl.ayan.presenter.ImplProductsPresenter
import com.atl.ayan.presenter.ImplSubTypesPresenter
import com.atl.ayan.presenter.ProductsPresenter
import com.atl.ayan.presenter.SubTypesPresenter
import com.atl.ayan.presenter.view.ProductsView
import com.atl.ayan.presenter.view.SubTypesView
import dagger.Module
import dagger.Provides

@Module
class ProductsModule(var productsView: ProductsView) {

    @Provides
    fun presenter(typesRepository: TypesRepository, cartRepository: CartRepository): ProductsPresenter {
        return ImplProductsPresenter(productsView, typesRepository, cartRepository)
    }
}