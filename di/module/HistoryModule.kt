package com.atl.ayan.di.module

import com.atl.ayan.model.repository.CartRepository
import com.atl.ayan.model.repository.HistoryRepository
import com.atl.ayan.model.repository.ProfileRepository
import com.atl.ayan.presenter.HistoryPresenter
import com.atl.ayan.presenter.ImplHistoryPresenter
import com.atl.ayan.presenter.view.HistoryView
import dagger.Module
import dagger.Provides

@Module
class HistoryModule(var historyView: HistoryView) {

    @Provides
    fun presenter(historyRepository: HistoryRepository): HistoryPresenter {
        return ImplHistoryPresenter(historyView, historyRepository)
    }
}