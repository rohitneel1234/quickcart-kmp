package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.data.OrderRepository
import com.rohitneel.quickcart.model.Address
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

private val demoAddresses = listOf(
    Address("home", "Home", "221B, MG Road, Nashik, Maharashtra"),
    Address("work", "Work", "Tech Park, 4th Floor, Nashik Road, Nashik"),
    Address("other", "Other", "12, Green Valley Society, Gangapur Road, Nashik")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onOrderPlaced: (String) -> Unit
) {
    var selectedAddressId by remember { mutableStateOf(demoAddresses.first().id) }
    val lines by CartRepository.lines.collectAsState()
    val subtotal by CartRepository.subtotal.collectAsState()
    val deliveryFee = if (subtotal >= 199.0) 0.0 else 25.0
    val handlingFee = 4.0
    val total = subtotal + deliveryFee + handlingFee

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth().background(BrandColors.Surface).padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Payable", fontWeight = FontWeight.Bold)
                    Text("₹${total.roundToInt()}", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = {
                        val address = demoAddresses.first { it.id == selectedAddressId }
                        val order = OrderRepository.placeOrder(lines, total, address.fullAddress)
                        CartRepository.clear()
                        onOrderPlaced(order.id)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green),
                    enabled = lines.isNotEmpty()
                ) {
                    Text("Place Order (Pay on Delivery)", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(BrandColors.Background).padding(16.dp)) {
            Text("Delivering to", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            demoAddresses.forEach { address ->
                AddressRow(
                    address = address,
                    selected = address.id == selectedAddressId,
                    onSelect = { selectedAddressId = address.id }
                )
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(16.dp))
            Text("Order Summary", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("${lines.sumOf { it.quantity }} items · ₹${subtotal.roundToInt()}", fontSize = 13.sp, color = BrandColors.TextSecondary)
        }
    }
}

@Composable
private fun AddressRow(address: Address, selected: Boolean, onSelect: () -> Unit) {
    val icon = when (address.label) {
        "Home" -> Icons.Filled.Home
        "Work" -> Icons.Filled.Work
        else -> Icons.Filled.LocationOn
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BrandColors.Surface)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) BrandColors.Green else BrandColors.Divider,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onSelect)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = BrandColors.Green)
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(address.label, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(address.fullAddress, fontSize = 12.sp, color = BrandColors.TextSecondary, maxLines = 2)
        }
        if (selected) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = BrandColors.Green)
        }
    }
}
