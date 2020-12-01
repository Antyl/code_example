package com.atl.ayan.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    var idProduct: String,
    var nameProduct: String,
    var descriptionProduct: String,
    var propertyOne: String,
    var propertyTwo: String,
    var propertyThree: String,
    var propertyFour: String,
    var unit: String,
    var ratio: Double,
    var type: String,
    var price: Double,
    var priceForCard: Double,
    var image: Boolean
) : Parcelable