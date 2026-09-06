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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.rohitneel.quickcart.data.OrderRepository
import com.rohitneel.quickcart.model.Order
import com.rohitneel.quickcart.model.OrderStatus
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

@Composable
fun OrderSuccessScreen(orderId: String, onContinueShopping: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(BrandColors.Background).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = BrandColors.Green, modifier = Modifier.height(72.dp))
        Spacer(Modifier.height(16.dp))
        Text("Order Placed!", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Order #$orderId is on its way. Arriving in 8-10 minutes.", color = BrandColors.TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onContinueShopping,
            colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green),
            modifier = Modifier.height(48.dp)
        ) {
            Text("Continue Shopping", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun OrdersScreen() {
    val orders by OrderRepository.orders.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(BrandColors.Background)) {
        Text(
            "Your Orders",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Receipt, contentDescription = null, tint = BrandColors.TextSecondary, modifier = Modifier.height(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("No orders yet", color = BrandColors.TextSecondary)
                }
            }
        } else {
            LazyColumn(contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)) {
                items(orders) { order -> OrderCard(order) }
            }
        }
    }
}

@Composable
private fun OrderCard(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BrandColors.Surface)
            .padding(14.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Order #${order.id}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(statusLabel(order.status), color = BrandColors.Green, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            order.lines.joinToString(", ") { "${it.product.name} x${it.quantity}" },
            fontSize = 12.sp,
            color = BrandColors.TextSecondary,
            maxLines = 2
        )
        Spacer(Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Deliver to: ${order.deliveryAddress}", fontSize = 11.sp, color = BrandColors.TextSecondary, maxLines = 1, modifier = Modifier.weight(1f))
            Text("₹${order.total.roundToInt()}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

private fun statusLabel(status: OrderStatus): String = when (status) {
    OrderStatus.PLACED -> "Order Placed"
    OrderStatus.PACKED -> "Packed"
    OrderStatus.OUT_FOR_DELIVERY -> "Out for Delivery"
    OrderStatus.DELIVERED -> "Delivered"
}

// Small local alias so this file doesn't need an extra Material3 Icon import block above.
@Composable
private fun Icon(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String?,
    tint: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) = androidx.compose.material3.Icon(imageVector, contentDescription, modifier, tint)
