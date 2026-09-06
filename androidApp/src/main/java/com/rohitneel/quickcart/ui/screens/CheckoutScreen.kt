package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.OrderRepository
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(onBack: () -> Unit, onOrderPlaced: (String) -> Unit) {
    val lines by CartRepository.lines.collectAsState()
    val subtotal by CartRepository.subtotal.collectAsState()
    
    var address by remember { mutableStateOf("221B, MG Road, Nashik") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        },
        bottomBar = {
            CheckoutBottomBar(subtotal) {
                val order = OrderRepository.placeOrder(lines, subtotal, address)
                CartRepository.clear()
                onOrderPlaced(order.id)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BrandColors.Background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Delivery Address", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Home Address") }
            )
            
            Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandColors.Surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    lines.forEach { line ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${line.quantity}x ${line.product.name}", fontSize = 14.sp)
                            Text("₹${line.lineTotal.roundToInt()}", fontSize = 14.sp)
                        }
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", fontWeight = FontWeight.Bold)
                        Text("₹${subtotal.roundToInt()}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CheckoutBottomBar(total: Double, onPlaceOrder: () -> Unit) {
    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        color = BrandColors.Surface
    ) {
        Button(
            onClick = onPlaceOrder,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
        ) {
            Text("Place Order - ₹${total.roundToInt()}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
