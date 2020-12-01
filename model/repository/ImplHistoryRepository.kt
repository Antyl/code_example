package com.atl.ayan.model.repository

import com.atl.ayan.model.entity.OrderHistory
import com.atl.ayan.model.room.RoomDatabaseUtils
import com.atl.ayan.network.NetworkService
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImplHistoryRepository : HistoryRepository {

    override fun getHistory(): Single<MutableList<OrderHistory>> {
        return Single.create { observer ->
            val profile = RoomDatabaseUtils.getProfile()
            NetworkService()
                .getInstance()
                .getServerAPI()
                .getHistory(profile.phone)
                .enqueue(object : Callback<MutableList<OrderHistory>> {
                    override fun onFailure(call: Call<MutableList<OrderHistory>>, t: Throwable) {
                        observer.onError(Error("Отсутствует связь с сервером, проверьте соединение"))
                    }

                    override fun onResponse(
                        call: Call<MutableList<OrderHistory>>,
                        response: Response<MutableList<OrderHistory>>
                    ) {
                        when (response.code()) {
                            200 -> {
                                observer.onSuccess(response.body()!!)
                            }
                            else -> {
                                observer.onError(Error("Неизвестная ошибка сервера, попробуйте позже"))
                            }
                        }
                    }
                })
        }
    }
}