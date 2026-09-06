package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen() {
    val orders by OrderRepository.orders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order History", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        }
    ) { padding ->
        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No orders yet", color = BrandColors.TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderItemCard(order)
                }
            }
        }
    }
}

@Composable
private fun OrderItemCard(order: Order) {
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

