package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    val results = remember(query) { GroceryRepository.search(query) }
    val cartLines by CartRepository.lines.collectAsState()

    fun qtyFor(productId: String) = cartLines.firstOrNull { it.product.id == productId }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search products...") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        }
    ) { padding ->
        if (query.isEmpty()) {
            SearchPlaceholder(padding)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results, key = { it.id }) { product ->
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

@Composable
fun SearchPlaceholder(padding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Search for items", style = MaterialTheme.typography.bodyLarge, color = BrandColors.TextSecondary)
    }
}
