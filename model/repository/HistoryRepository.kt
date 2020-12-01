package com.atl.ayan.model.repository

import com.atl.ayan.model.entity.OrderHistory
import io.reactivex.Single

interface HistoryRepository {
    fun getHistory(): Single<MutableList<OrderHistory>>
}