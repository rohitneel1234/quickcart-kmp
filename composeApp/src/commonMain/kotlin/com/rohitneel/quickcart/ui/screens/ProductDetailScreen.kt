package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
    onAdd: (com.rohitneel.quickcart.model.Product, Offset) -> Unit,
    onRemove: (com.rohitneel.quickcart.model.Product, Offset) -> Unit
) {
    val product = GroceryRepository.getProduct(productId) ?: return
    val cartLines by CartRepository.lines.collectAsState()
    val quantity = cartLines.firstOrNull { it.product.id == productId }?.quantity ?: 0
    var iconOffset by remember { mutableStateOf(Offset.Zero) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().background(BrandColors.Surface).padding(16.dp)) {
                if (quantity == 0) {
                    Button(
                        onClick = { onAdd(product, iconOffset) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
                    ) {
                        Text("Add to Cart · ₹${product.price.roundToInt()}", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            QuantityStepper(
                                quantity = quantity,
                                onAdd = { onAdd(product, iconOffset) },
                                onRemove = { onRemove(product, iconOffset) },
                                fillWidth = true
                            )
                        }
                        Button(
                            onClick = onGoToCart,
                            modifier = Modifier.weight(1f).height(34.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                        ) {
                            Text("View Cart", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BrandColors.Background)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(BrandColors.Surface)
                    .onGloballyPositioned { iconOffset = it.positionInRoot() },
                contentAlignment = Alignment.Center
            ) {
                Text(product.emoji, fontSize = 96.sp)
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏱ ${product.deliveryEta}", fontSize = 13.sp, color = BrandColors.TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    RatingBadge(rating = product.rating)
                }
                Spacer(Modifier.height(8.dp))
                product.brand?.let {
                    Text(it, fontSize = 12.sp, color = BrandColors.TextSecondary)
                }
                Text(product.name, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Text(product.unit, fontSize = 13.sp, color = BrandColors.TextSecondary)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("₹${product.price.roundToInt()}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    if (product.mrp > product.price) {
                        Text(
                            "₹${product.mrp.roundToInt()}",
                            fontSize = 14.sp,
                            color = BrandColors.TextSecondary,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "${product.discountPercent}% OFF",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandColors.Discount
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("Product details", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(product.description, fontSize = 13.sp, color = BrandColors.TextSecondary, lineHeight = 18.sp)
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
