package com.rohitneel.quickcart.data

import com.rohitneel.quickcart.model.Category
import com.rohitneel.quickcart.model.Product

object GroceryRepository {

    fun getCategories(): List<Category> = SampleData.categories

    fun getCategory(id: String): Category? = SampleData.categories.firstOrNull { it.id == id }

    fun getProductsForCategory(categoryId: String): List<Product> =
        SampleData.allProducts.filter { it.categoryId == categoryId }

    fun getProduct(id: String): Product? = SampleData.allProducts.firstOrNull { it.id == id }

    fun getTrendingProducts(): List<Product> =
        SampleData.trendingProductIds.mapNotNull { id -> getProduct(id) }

    fun search(query: String): List<Product> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        return SampleData.allProducts.filter {
            it.name.lowercase().contains(q) ||
                it.brand?.lowercase()?.contains(q) == true ||
                getCategory(it.categoryId)?.name?.lowercase()?.contains(q) == true
        }
    }
}
