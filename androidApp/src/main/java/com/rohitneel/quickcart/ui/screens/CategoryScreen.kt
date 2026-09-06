package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.GroceryRepository
import com.rohitneel.quickcart.ui.components.ProductCard
import com.rohitneel.quickcart.ui.theme.BrandColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryId: String,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onAdd: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit
) {
    val category = GroceryRepository.getCategory(categoryId) ?: return
    val products = GroceryRepository.getProductsForCategory(categoryId)
    val cartLines by CartRepository.lines.collectAsState()

    fun qtyFor(productId: String) = cartLines.firstOrNull { it.product.id == productId }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    quantity = qtyFor(product.id),
                    onAdd = { offset -> onAdd(product, offset) },
                    onRemove = { offset -> onRemove(product, offset) },
                    onClick = { onProductClick(product.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
