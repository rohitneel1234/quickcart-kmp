package com.rohitneel.quickcart.navigation

sealed class Screen {
    data object Home : Screen()
    data class Category(val categoryId: String) : Screen()
    data object Search : Screen()
    data object CategoriesList : Screen()
    data class ProductDetail(val productId: String) : Screen()
    data object Cart : Screen()
    data object Checkout : Screen()
    data class OrderSuccess(val orderId: String) : Screen()
    data object Orders : Screen()
}

enum class BottomTab { HOME, CATEGORIES, CART, ORDERS }

fun Screen.toBottomTab(): BottomTab? = when (this) {
    is Screen.Home -> BottomTab.HOME
    is Screen.CategoriesList -> BottomTab.CATEGORIES
    is Screen.Cart -> BottomTab.CART
    is Screen.Orders -> BottomTab.ORDERS
    else -> null
}
