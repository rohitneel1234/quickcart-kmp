package com.rohitneel.quickcart.data

import com.rohitneel.quickcart.model.CartLine
import com.rohitneel.quickcart.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Simple in-memory cart. A singleton object is enough for a single-user demo
 * app; swap for a DI-scoped instance if you add multi-account support.
 *
 * All derived flows ([lines], [totalItemCount], [subtotal]) are recomputed and
 * re-published every time the cart mutates, so `collectAsState()` in Compose
 * always sees fresh values.
 */
object CartRepository {

    // productId -> quantity
    private val quantities = mutableMapOf<String, Int>()

    private val _lines = MutableStateFlow<List<CartLine>>(emptyList())
    val lines: StateFlow<List<CartLine>> = _lines

    private val _totalItemCount = MutableStateFlow(0)
    val totalItemCount: StateFlow<Int> = _totalItemCount

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal: StateFlow<Double> = _subtotal

    fun quantityOf(productId: String): Int = quantities[productId] ?: 0

    fun add(product: Product) {
        quantities[product.id] = (quantities[product.id] ?: 0) + 1
        publish()
    }

    fun remove(product: Product) {
        val newQty = (quantities[product.id] ?: 0) - 1
        if (newQty <= 0) quantities.remove(product.id) else quantities[product.id] = newQty
        publish()
    }

    fun clear() {
        quantities.clear()
        publish()
    }

    private fun publish() {
        val currentLines = quantities.entries.mapNotNull { (productId, qty) ->
            GroceryRepository.getProduct(productId)?.let { product -> CartLine(product, qty) }
        }
        _lines.value = currentLines
        _totalItemCount.value = currentLines.sumOf { it.quantity }
        _subtotal.value = currentLines.sumOf { it.lineTotal }
    }
}
