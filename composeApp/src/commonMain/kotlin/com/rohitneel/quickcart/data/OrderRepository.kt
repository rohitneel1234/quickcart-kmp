package com.rohitneel.quickcart.data

import com.rohitneel.quickcart.model.CartLine
import com.rohitneel.quickcart.model.Order
import com.rohitneel.quickcart.model.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

object OrderRepository {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun placeOrder(lines: List<CartLine>, total: Double, address: String): Order {
        val order = Order(
            id = "QC${Random.nextInt(100000, 999999)}",
            lines = lines,
            total = total,
            placedAtEpochSeconds = 0L, // demo app: not showing a real timestamp
            status = OrderStatus.PLACED,
            deliveryAddress = address
        )
        _orders.value = listOf(order) + _orders.value
        return order
    }
}
