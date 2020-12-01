package com.atl.ayan.di.component

import com.atl.ayan.di.module.ProductsModule
import com.atl.ayan.di.scope.AppScope
import com.atl.ayan.ui.ProductsFragment
import dagger.Component

@AppScope
@Component(modules = [ProductsModule::class], dependencies = [AppComponent::class])
interface ProductsComponent {
    fun inject(productsFragment: ProductsFragment)
}