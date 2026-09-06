package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.GroceryRepository
import com.rohitneel.quickcart.ui.components.CategoryTile
import com.rohitneel.quickcart.ui.components.ProductCard
import com.rohitneel.quickcart.ui.components.SectionHeader
import com.rohitneel.quickcart.ui.theme.BrandColors

@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,
    onProductClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onAdd: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit
) {
    val categories = GroceryRepository.getCategories()
    val trending = GroceryRepository.getTrendingProducts()
    val quantities by CartRepository.lines.collectAsState()

    val groceryAndKitchenIds = listOf("fruits_veg", "atta_rice_dal", "masala_oil", "dairy_bread", "bakery", "meat_fish")
    val snacksAndDrinksIds = listOf("snacks", "beverages", "tea_coffee", "sweet_namkeen")

    fun qtyFor(productId: String) = quantities.firstOrNull { it.product.id == productId }?.quantity ?: 0

    LazyColumn(modifier = Modifier.fillMaxSize().background(BrandColors.Surface)) {
        item { DeliveryHeader() }
        item { SearchBar(onClick = onSearchClick) }
        item { PromoBanner() }
        
        item { SectionHeader(title = "Grocery & Kitchen") }
        item {
            CategoryGrid(
                categories = categories.filter { it.id in groceryAndKitchenIds },
                onCategoryClick = onCategoryClick
            )
        }

        item { SectionHeader(title = "Snacks & Drinks") }
        item {
            CategoryGrid(
                categories = categories.filter { it.id in snacksAndDrinksIds },
                onCategoryClick = onCategoryClick
            )
        }

        item { SectionHeader(title = "Popular Right Now") }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trending) { product ->
                    ProductCard(
                        product = product,
                        quantity = qtyFor(product.id),
                        onAdd = { offset -> onAdd(product, offset) },
                        onRemove = { offset -> onRemove(product, offset) },
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
private fun CategoryGrid(categories: List<com.rohitneel.quickcart.model.Category>, onCategoryClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        val rows = categories.chunked(4)
        rows.forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowCategories.forEach { category ->
                    CategoryTile(
                        category = category,
                        onClick = { onCategoryClick(category.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(4 - rowCategories.size) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun DeliveryHeader() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Delivery in", fontSize = 12.sp, color = BrandColors.TextSecondary)
        }
        Text("8 minutes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BrandColors.TextPrimary)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.LocationOn, contentDescription = null, tint = BrandColors.TextSecondary, modifier = Modifier.height(14.dp))
            Spacer(Modifier.width(4.dp))
            Text("Home - 221B, MG Road, Nashik", fontSize = 12.sp, color = BrandColors.TextSecondary, maxLines = 1)
        }
    }
}

@Composable
private fun SearchBar(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BrandColors.Surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = BrandColors.TextSecondary)
        Spacer(Modifier.width(8.dp))
        Text("Search for atta, dal, oil, fruits...", color = BrandColors.TextSecondary, fontSize = 13.sp)
    }
}

@Composable
private fun PromoBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(BrandColors.Yellow)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Free delivery", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("On your first order above ₹199", fontSize = 12.sp)
        }
        Text("🛒", fontSize = 32.sp)
    }
}

