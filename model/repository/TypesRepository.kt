package com.atl.ayan.model.repository

import com.atl.ayan.model.entity.Product
import com.atl.ayan.model.entity.SubType
import com.atl.ayan.model.entity.Type
import io.reactivex.Single

interface TypesRepository {
    fun getProducts(type: String, subType: String): MutableList<Product>
}