package com.atl.ayan.di.module

import com.atl.ayan.model.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getDiscountRepository(): DiscountRepository {
        return ImplDiscountRepository()
    }

    @Singleton
    @Provides
    fun getAddressesRepository(): AddressesRepository {
        return ImplAddressesRepository()
    }

    @Singleton
    @Provides
    fun getCartRepository(): CartRepository {
        return ImplCartRepository()
    }

    @Singleton
    @Provides
    fun getProfileRepository(): ProfileRepository {
        return ImplProfileRepository()
    }

    @Singleton
    @Provides
    fun getHistoryRepository(): HistoryRepository {
        return ImplHistoryRepository()
    }

    @Singleton
    @Provides
    fun getTypesRepository(): TypesRepository {
        return ImplTypesRepository()
    }
}