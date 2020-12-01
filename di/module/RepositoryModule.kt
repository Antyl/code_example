package com.atl.ayan.di.module

import com.atl.ayan.model.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

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