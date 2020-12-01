package com.atl.ayan.presenter

import com.atl.ayan.model.repository.HistoryRepository
import com.atl.ayan.presenter.view.HistoryView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImplHistoryPresenter(var historyView: HistoryView,
                           var historyRepository: HistoryRepository)
    : HistoryPresenter {

    private val disposables = CompositeDisposable()

    override fun getHistory() {
        historyView.showLoading()
        disposables.add(historyRepository.getHistory()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                historyView.hideLoading()
                if (it.isNotEmpty()) {
                    historyView.setData(it)
                } else {
                    historyView.showEmpty()
                }
            }, {
                historyView.hideLoading()
                historyView.showError(it.message.toString())
            }))
    }
}