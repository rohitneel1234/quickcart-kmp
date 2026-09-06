package com.rohitneel.quickcart.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rohitneel.quickcart.model.CartLine
import com.rohitneel.quickcart.model.Category
import com.rohitneel.quickcart.model.Product
import com.rohitneel.quickcart.ui.theme.BrandColors
import kotlin.math.roundToInt

@Composable
fun CategoryTile(category: Category, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(category.colorHex)),
            contentAlignment = Alignment.Center
        ) {
            Text(category.emoji, fontSize = 28.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text(
            category.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 14.sp,
            color = BrandColors.TextPrimary,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ProductCard(
    product: Product,
    quantity: Int,
    onAdd: (androidx.compose.ui.geometry.Offset) -> Unit,
    onRemove: (androidx.compose.ui.geometry.Offset) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var iconOffset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

    Column(
        modifier = modifier
            .width(150.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(BrandColors.Surface)
            .border(BorderStroke(1.dp, BrandColors.Divider), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .clip(RoundedCornerShape(10.dp))
                .background(BrandColors.Background)
                .onGloballyPositioned { iconOffset = it.positionInRoot() },
            contentAlignment = Alignment.Center
        ) {
            Text(product.emoji, fontSize = 40.sp)
            if (product.discountPercent > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(topStart = 10.dp, bottomEnd = 8.dp))
                        .background(BrandColors.Discount)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("${product.discountPercent}% OFF", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("⏱ ${product.deliveryEta}", fontSize = 11.sp, color = BrandColors.TextSecondary)
        Spacer(Modifier.height(4.dp))
        Text(
            product.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            color = BrandColors.TextPrimary
        )
        Text(product.unit, fontSize = 11.sp, color = BrandColors.TextSecondary)
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("₹${product.price.roundToInt()}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                if (product.mrp > product.price) {
                    Text(
                        "₹${product.mrp.roundToInt()}",
                        fontSize = 11.sp,
                        color = BrandColors.TextSecondary,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        if (quantity == 0) {
            Button(
                onClick = { onAdd(iconOffset) },
                modifier = Modifier.fillMaxWidth().height(34.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandColors.Surface,
                    contentColor = BrandColors.Green
                ),
                border = BorderStroke(1.dp, BrandColors.Green),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Text("ADD", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        } else {
            QuantityStepper(
                quantity = quantity,
                onAdd = { onAdd(iconOffset) },
                onRemove = { onRemove(iconOffset) },
                fillWidth = true
            )
        }
    }
}

@Composable
fun QuantityStepper(
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    fillWidth: Boolean = false
) {
    Row(
        modifier = (if (fillWidth) Modifier.fillMaxWidth() else Modifier)
            .height(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BrandColors.Green),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StepperIconButton(icon = Icons.Filled.Remove, onClick = onRemove)
        Text(
            "$quantity",
            color = androidx.compose.ui.graphics.Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
        StepperIconButton(icon = Icons.Filled.Add, onClick = onAdd)
    }
}

@Composable
private fun StepperIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = androidx.compose.ui.graphics.Color.White, modifier = Modifier.size(16.dp))
    }
}

@Composable
fun RatingBadge(rating: Double) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(BrandColors.GreenDark)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Star, contentDescription = null, tint = BrandColors.Yellow, modifier = Modifier.size(12.dp))
        Spacer(Modifier.width(2.dp))
        Text("$rating", color = androidx.compose.ui.graphics.Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionHeader(title: String, actionText: String? = null, onActionClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        if (actionText != null) {
            Text(
                actionText,
                color = BrandColors.Green,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onActionClick?.invoke() }
            )
        }
    }
}

@Composable
fun FloatingViewCart(
    cartLines: List<CartLine>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onPositioned: (androidx.compose.ui.geometry.Offset) -> Unit = {}
) {
    if (cartLines.isEmpty()) return

    val totalCount = cartLines.sumOf { it.quantity }

    Box(
        modifier = modifier
            .padding(16.dp)
            .height(56.dp)
            .clip(CircleShape)
            .background(BrandColors.GreenDark)
            .clickable(onClick = onClick)
            .onGloballyPositioned { onPositioned(it.positionInRoot()) }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Take the 3 most recently added items
                val recentLines = cartLines.reversed().take(3)
                val iconCount = recentLines.size
                // Container width for stacked emojis: first one is 32dp, subsequent ones add 20dp shift
                val emojisWidth = if (iconCount > 0) (32 + (iconCount - 1) * 20).dp else 0.dp

                if (iconCount > 0) {
                    Box(
                        modifier = Modifier.width(emojisWidth),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        recentLines.forEachIndexed { index, line ->
                            Box(
                                modifier = Modifier
                                    .padding(start = (index * 20).dp)
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(line.product.emoji, fontSize = 16.sp)
                            }
                        }
                    }
                }

                Spacer(Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            "View cart",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            "$totalCount ${if (totalCount == 1) "item" else "items"}",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}
