package com.atl.ayan.presenter.view

import com.atl.ayan.model.entity.OrderHistory

interface HistoryView {
    fun setData(orderHistoryList: MutableList<OrderHistory>)
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showEmpty()
}