package com.rohitneel.quickcart.model

data class Category(
    val id: String,
    val name: String,
    val emoji: String,
    val colorHex: Long
)

data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val brand: String? = null,
    val unit: String,
    val price: Double,
    val mrp: Double,
    val emoji: String,
    val rating: Double = 4.2,
    val productDescription: String = "",
    val deliveryEta: String = "8 mins"
) {
    val discountPercent: Int
        get() = if (mrp > price) (((mrp - price) / mrp) * 100).toInt() else 0
}

data class CartLine(
    val product: Product,
    val quantity: Int
) {
    val lineTotal: Double get() = product.price * quantity
}

enum class OrderStatus { PLACED, PACKED, OUT_FOR_DELIVERY, DELIVERED }

data class Order(
    val id: String,
    val lines: List<CartLine>,
    val total: Double,
    val placedAtEpochSeconds: Long,
    val status: OrderStatus,
    val deliveryAddress: String
)

data class Address(
    val id: String,
    val label: String,
    val fullAddress: String
)
