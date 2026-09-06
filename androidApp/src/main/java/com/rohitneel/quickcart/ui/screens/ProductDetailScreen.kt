package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.GroceryRepository
import com.rohitneel.quickcart.ui.components.QuantityStepper
import com.rohitneel.quickcart.ui.components.RatingBadge
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit,
    onGoToCart: () -> Unit,
    onAdd: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit
) {
    val product = GroceryRepository.getProduct(productId) ?: return
    val cartLines by CartRepository.lines.collectAsState()
    val quantity = cartLines.firstOrNull { it.product.id == productId }?.quantity ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            ProductDetailBottomBar(product, quantity, onAdd, onRemove, onGoToCart)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(BrandColors.Surface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(BrandColors.Background),
                contentAlignment = Alignment.Center
            ) {
                Text(product.emoji, fontSize = 120.sp)
            }
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.brand ?: "Fresh",
                    style = MaterialTheme.typography.labelLarge,
                    color = BrandColors.Green
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.unit,
                    style = MaterialTheme.typography.bodyLarge,
                    color = BrandColors.TextSecondary
                )
                
                Spacer(Modifier.height(16.dp))
                
                RatingBadge(product.rating)
                
                Spacer(Modifier.height(24.dp))
                
                Text(
                    text = "Product Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = product.description.ifBlank { "No description available." },
                    style = MaterialTheme.typography.bodyLarge,
                    color = BrandColors.TextPrimary,
                    lineHeight = 22.sp
                )
                
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    product: com.rohitneel.quickcart.model.Product,
    quantity: Int,
    onAdd: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, androidx.compose.ui.geometry.Offset) -> Unit,
    onGoToCart: () -> Unit
) {
    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        color = BrandColors.Surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("₹${product.price.roundToInt()}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                if (product.mrp > product.price) {
                    Text(
                        "MRP ₹${product.mrp.roundToInt()}",
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                    )
                }
            }
            
            if (quantity == 0) {
                Button(
                    onClick = { onAdd(product, androidx.compose.ui.geometry.Offset.Zero) },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
                ) {
                    Text("Add to Cart", fontWeight = FontWeight.Bold)
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    QuantityStepper(
                        quantity = quantity,
                        onAdd = { onAdd(product, androidx.compose.ui.geometry.Offset.Zero) },
                        onRemove = { onRemove(product, androidx.compose.ui.geometry.Offset.Zero) }
                    )
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = onGoToCart,
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
                    ) {
                        Text("View Cart")
                    }
                }
            }
        }
    }
}
