package com.atl.ayan.di.component

import com.atl.ayan.di.module.HistoryModule
import com.atl.ayan.di.scope.AppScope
import com.atl.ayan.ui.HistoryActivity
import dagger.Component

@AppScope
@Component(modules = [HistoryModule::class], dependencies = [AppComponent::class])
interface HistoryComponent {
    fun inject(historyActivity: HistoryActivity)
}