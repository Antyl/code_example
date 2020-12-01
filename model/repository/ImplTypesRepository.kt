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
}