package model.entity

data class OrderHistory (
    var orderNumber: String,
    var date: String,
    var time: String,
    var orderSum: Double,
    var address: String,
    var products: MutableList<ProductHistory>
)