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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.rohitneel.quickcart.ui.components.QuantityStepper
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {
    val lines by CartRepository.lines.collectAsState()
    val subtotal by CartRepository.subtotal.collectAsState()
    val deliveryFee = if (subtotal >= 199.0 || subtotal == 0.0) 0.0 else 25.0
    val handlingFee = if (lines.isEmpty()) 0.0 else 4.0
    val total = subtotal + deliveryFee + handlingFee

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        },
        bottomBar = {
            if (lines.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth().background(BrandColors.Surface).padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("To Pay", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("₹${total.roundToInt()}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = onCheckout,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
                    ) {
                        Text("Proceed to Checkout", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        if (lines.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = BrandColors.TextSecondary, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Your cart is empty", color = BrandColors.TextSecondary)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(lines, key = { it.product.id }) { line ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BrandColors.Surface)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(BrandColors.Background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(line.product.emoji, fontSize = 22.sp)
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(line.product.name, fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1)
                                    Text(line.product.unit, fontSize = 11.sp, color = BrandColors.TextSecondary)
                                    Text("₹${line.product.price.roundToInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            QuantityStepper(
                                quantity = line.quantity,
                                onAdd = { CartRepository.add(line.product) },
                                onRemove = { CartRepository.remove(line.product) }
                            )
                        }
                        Divider(color = BrandColors.Divider)
                    }
                    item { BillSummary(subtotal, deliveryFee, handlingFee, total) }
                }
            }
        }
    }
}

@Composable
private fun BillSummary(subtotal: Double, deliveryFee: Double, handlingFee: Double, total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BrandColors.Surface)
            .padding(16.dp)
    ) {
        Text("Bill Details", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        BillRow("Items total", "₹${subtotal.roundToInt()}")
        BillRow("Delivery fee", if (deliveryFee == 0.0) "FREE" else "₹${deliveryFee.roundToInt()}")
        BillRow("Handling fee", "₹${handlingFee.roundToInt()}")
        Divider(modifier = Modifier.padding(vertical = 8.dp), color = BrandColors.Divider)
        BillRow("Grand total", "₹${total.roundToInt()}", bold = true)
    }
}

@Composable
private fun BillRow(label: String, value: String, bold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
        Text(value, fontSize = 13.sp, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
    }
}
