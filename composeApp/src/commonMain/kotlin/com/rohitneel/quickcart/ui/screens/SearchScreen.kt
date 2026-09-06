package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.GroceryRepository
import com.rohitneel.quickcart.ui.components.ProductCard
import com.rohitneel.quickcart.ui.theme.BrandColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onAdd: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val results = if (query.isBlank()) emptyList() else GroceryRepository.search(query)
    val cartLines by CartRepository.lines.collectAsState()
    fun qtyFor(productId: String) = cartLines.firstOrNull { it.product.id == productId }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search groceries, brands...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = BrandColors.Background,
                            focusedContainerColor = BrandColors.Background
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background)) {
            when {
                query.isBlank() -> EmptyState(icon = Icons.Filled.Search, message = "Search for atta, milk, snacks, and more")
                results.isEmpty() -> EmptyState(icon = Icons.Filled.Search, message = "No products found for \"$query\"")
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(results) { product ->
                        ProductCard(
                            product = product,
                            quantity = qtyFor(product.id),
                            onAdd = { offset -> onAdd(product, offset) },
                            onRemove = { offset -> onRemove(product, offset) },
                            onClick = { onProductClick(product.id) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message, color = BrandColors.TextSecondary)
    }
}
