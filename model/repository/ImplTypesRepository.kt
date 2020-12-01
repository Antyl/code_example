package com.atl.ayan.model.repository

import android.util.Log
import com.atl.ayan.model.entity.Product
import com.atl.ayan.model.entity.SubType
import com.atl.ayan.model.entity.Type
import com.atl.ayan.model.room.CartBoxesEntity
import com.atl.ayan.model.room.RoomDatabaseUtils
import com.atl.ayan.network.NetworkService
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ImplTypesRepository: TypesRepository{

    private var allTypes: MutableList<Type> = ArrayList()

    override fun fetchTypes(): Single<MutableList<Type>> {
        return Single.create { observer ->
            if (allTypes.isEmpty()) {
                val address = RoomDatabaseUtils.getAddress()
                NetworkService()
                    .getInstance()
                    .getServerAPI()
                    .getTypes(address.idShop)
                    .enqueue(object : Callback<MutableList<Type>> {
                        override fun onFailure(call: Call<MutableList<Type>>, t: Throwable) {
                            observer.onError(t)
                        }

                        override fun onResponse(
                            call: Call<MutableList<Type>>,
                            response: Response<MutableList<Type>>
                        ) {
                            when (response.code()) {
                                200 -> {
                                    allTypes = response.body()!!
                                    observer.onSuccess(allTypes)
                                }
                                201 -> observer.onError(Exception("Fail"))
                                else -> observer.onError(Exception("Fail"))
                            }
                        }
                    })
            } else {
                observer.onSuccess(allTypes)
            }
        }
    }

    override fun getTypes(): MutableList<Type>? {
        return allTypes
    }

    override fun clearTypes() {
        allTypes.clear()
    }

    override fun getSubTypes(type: String): MutableList<SubType> {
        val subTypes = ArrayList<SubType>()
        allTypes.forEach {
            if (it.type == type) {
                subTypes.addAll(it.subTypes)
            }
        }
        return subTypes
    }

    override fun getProducts(type: String, subType: String): MutableList<Product> {
        val products = ArrayList<Product>()
        allTypes.forEach { typeInside ->
            if (typeInside.type == type) {
                typeInside.subTypes.forEach {
                    if (it.subType == subType) {
                        products.addAll(it.products)
                    }
                }
            }
        }
        products.sortBy {
            if (it.priceForCard > 0.0 && it.priceForCard < it.price)
                it.priceForCard - it.price
            else
                it.price
        }
        return products
    }

    override fun getBoxes() {
        NetworkService()
            .getInstance()
            .getServerAPI()
            .getBoxes()
            .enqueue(object : Callback<MutableList<CartBoxesEntity>> {

                override fun onFailure(call: Call<MutableList<CartBoxesEntity>>, t: Throwable) {}

                override fun onResponse(
                    call: Call<MutableList<CartBoxesEntity>>,
                    response: Response<MutableList<CartBoxesEntity>>
                ) {
                    RoomDatabaseUtils.initBoxes(response.body()!!)
                }
            })
    }
}