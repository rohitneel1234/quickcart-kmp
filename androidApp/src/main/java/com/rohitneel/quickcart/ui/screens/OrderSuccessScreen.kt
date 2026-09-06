package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.ui.theme.BrandColors

@Composable
fun OrderSuccessScreen(orderId: String, onContinueShopping: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎉", fontSize = 80.sp)
        Spacer(Modifier.height(24.dp))
        Text("Order Placed Successfully!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text("Order ID: #$orderId", style = MaterialTheme.typography.bodyLarge, color = BrandColors.TextSecondary)
        Spacer(Modifier.height(48.dp))
        Button(
            onClick = onContinueShopping,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BrandColors.Green)
        ) {
            Text("Continue Shopping")
        }
    }
}
