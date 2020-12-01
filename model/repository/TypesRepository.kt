package com.atl.ayan.model.repository

import com.atl.ayan.model.entity.Product
import com.atl.ayan.model.entity.SubType
import com.atl.ayan.model.entity.Type
import io.reactivex.Single

interface TypesRepository {
    fun fetchTypes(): Single<MutableList<Type>>
    fun getTypes(): MutableList<Type>?
    fun clearTypes()
    fun getSubTypes(type: String): MutableList<SubType>
    fun getProducts(type: String, subType: String): MutableList<Product>
    fun getBoxes()
}