package com.atl.ayan.di.component

import com.atl.ayan.App
import com.atl.ayan.di.module.AppModule
import com.atl.ayan.di.module.RepositoryModule
import com.atl.ayan.model.repository.*
import com.atl.ayan.model.room.AppDatabase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(app: App)
    fun getTypesRepository(): TypesRepository
    fun getNotificationRepository(): HistoryRepository
}