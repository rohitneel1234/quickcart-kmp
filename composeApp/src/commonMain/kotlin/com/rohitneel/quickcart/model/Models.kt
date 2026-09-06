package com.rohitneel.quickcart.model

/**
 * A top-level grocery category, e.g. "Fruits & Vegetables".
 * [emoji] is used as a lightweight, dependency-free stand-in for a product icon/image.
 */
data class Category(
    val id: String,
    val name: String,
    val emoji: String,
    val colorHex: Long
)

/**
 * A single sellable grocery item.
 */
data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val brand: String? = null,
    val unit: String,          // e.g. "500 g", "1 L", "6 pcs"
    val price: Double,         // selling price
    val mrp: Double,           // original price (for showing discount)
    val emoji: String,
    val rating: Double = 4.2,
    val description: String = "",
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
    val label: String,   // Home / Work / Other
    val fullAddress: String
)
