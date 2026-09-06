package com.rohitneel.quickcart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.rohitneel.quickcart.data.CartRepository
import com.rohitneel.quickcart.navigation.BottomTab
import com.rohitneel.quickcart.navigation.Screen
import com.rohitneel.quickcart.navigation.toBottomTab
import com.rohitneel.quickcart.ui.screens.*
import com.rohitneel.quickcart.ui.components.FloatingViewCart
import com.rohitneel.quickcart.ui.theme.QuickCartTheme

data class AnimationState(
    val id: Long,
    val emoji: String,
    val start: Offset,
    val end: Offset,
    val isRemoving: Boolean = false
)

@Composable
fun MainApp() {
    QuickCartTheme {
        var screen by remember { mutableStateOf<Screen>(Screen.Home) }
        val cartLines by CartRepository.lines.collectAsState()
        val cartCount by CartRepository.totalItemCount.collectAsState()
        val activeTab = screen.toBottomTab()

        var cartPosition by remember { mutableStateOf(Offset.Zero) }
        var activeAnimations by remember { mutableStateOf(emptyList<AnimationState>()) }

        val onAdd: (com.rohitneel.quickcart.model.Product, Offset) -> Unit = { product, offset ->
            CartRepository.add(product)
            activeAnimations = activeAnimations + AnimationState(
                id = System.currentTimeMillis() + product.hashCode(),
                emoji = product.emoji,
                start = offset,
                end = cartPosition
            )
        }

        val onRemove: (com.rohitneel.quickcart.model.Product, Offset) -> Unit = { product, offset ->
            CartRepository.remove(product)
            activeAnimations = activeAnimations + AnimationState(
                id = System.currentTimeMillis() + product.hashCode() + 1,
                emoji = product.emoji,
                start = cartPosition,
                end = offset.copy(y = -100f),
                isRemoving = true
            )
        }

        Scaffold(
            bottomBar = {
                Column {
                    if (activeTab != BottomTab.CART && activeTab!= BottomTab.ORDERS && screen!= Screen.Checkout) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            FloatingViewCart(
                                cartLines = cartLines,
                                onClick = { screen = Screen.Cart },
                                onPositioned = { cartPosition = it }
                            )
                        }
                    }
                    if (activeTab != null) {
                        val isCartNotEmpty = activeTab == BottomTab.CART && cartLines.isNotEmpty()
                        if (!isCartNotEmpty) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = activeTab == BottomTab.HOME,
                                    onClick = { screen = Screen.Home },
                                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                                    label = { Text("Home") }
                                )
                                NavigationBarItem(
                                    selected = activeTab == BottomTab.CATEGORIES,
                                    onClick = { screen = Screen.CategoriesList },
                                    icon = { Icon(Icons.Filled.Widgets, contentDescription = "Categories") },
                                    label = { Text("Categories") }
                                )
                                NavigationBarItem(
                                    selected = activeTab == BottomTab.CART,
                                    onClick = { screen = Screen.Cart },
                                    icon = {
                                        BadgedBox(badge = { if (cartCount > 0) Badge { Text("$cartCount") } }) {
                                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                                        }
                                    },
                                    label = { Text("Cart") }
                                )
                                NavigationBarItem(
                                    selected = activeTab == BottomTab.ORDERS,
                                    onClick = { screen = Screen.Orders },
                                    icon = { Icon(Icons.Filled.Receipt, contentDescription = "Orders") },
                                    label = { Text("Orders") }
                                )
                            }
                        }
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.padding(padding)) {
                    when (val current = screen) {
                        is Screen.Home -> HomeScreen(
                            onCategoryClick = { id -> screen = Screen.Category(id) },
                            onProductClick = { id -> screen = Screen.ProductDetail(id) },
                            onSearchClick = { screen = Screen.Search },
                            onAdd = { p, o -> onAdd(p, o) },
                            onRemove = { p, o -> onRemove(p, o) }
                        )
                        is Screen.Category -> CategoryScreen(
                            categoryId = current.categoryId,
                            onBack = { screen = Screen.Home },
                            onProductClick = { id -> screen = Screen.ProductDetail(id) },
                            onAdd = { p, o -> onAdd(p, o) },
                            onRemove = { p, o -> onRemove(p, o) }
                        )
                        is Screen.CategoriesList -> CategoriesListScreen(
                            onCategoryClick = { id -> screen = Screen.Category(id) }
                        )
                        is Screen.Search -> SearchScreen(
                            onBack = { screen = Screen.Home },
                            onProductClick = { id -> screen = Screen.ProductDetail(id) },
                            onAdd = { p, o -> onAdd(p, o) },
                            onRemove = { p, o -> onRemove(p, o) }
                        )
                        is Screen.ProductDetail -> ProductDetailScreen(
                            productId = current.productId,
                            onBack = { screen = Screen.Home },
                            onGoToCart = { screen = Screen.Cart },
                            onAdd = { p, o -> onAdd(p, o) },
                            onRemove = { p, o -> onRemove(p, o) }
                        )
                        is Screen.Cart -> CartScreen(
                            onBack = { screen = Screen.Home },
                            onCheckout = { screen = Screen.Checkout }
                        )
                        is Screen.Checkout -> CheckoutScreen(
                            onBack = { screen = Screen.Cart },
                            onOrderPlaced = { orderId -> screen = Screen.OrderSuccess(orderId) }
                        )
                        is Screen.OrderSuccess -> OrderSuccessScreen(
                            orderId = current.orderId,
                            onContinueShopping = { screen = Screen.Home }
                        )
                        is Screen.Orders -> OrdersScreen()
                    }
                }

                activeAnimations.forEach { anim ->
                    key(anim.id) {
                        FlyingEmoji(anim) {
                            activeAnimations = activeAnimations.filter { it.id != anim.id }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlyingEmoji(state: AnimationState, onEnd: () -> Unit) {
    val x = remember { Animatable(state.start.x) }
    val y = remember { Animatable(state.start.y) }
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        val duration = 600
        launch {
            x.animateTo(state.end.x, tween(duration, easing = LinearEasing))
        }
        launch {
            y.animateTo(state.end.y, tween(duration, easing = LinearEasing))
        }
        launch {
            scale.animateTo(if (state.isRemoving) 0.5f else 1.2f, tween(duration / 2))
            scale.animateTo(if (state.isRemoving) 0f else 0.5f, tween(duration / 2))
        }
        if (state.isRemoving) {
            launch {
                alpha.animateTo(0f, tween(duration))
            }
        }
        onEnd()
    }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(x.value.toInt(), y.value.toInt())
            }
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            state.emoji,
            fontSize = (30 * scale.value).sp,
            modifier = Modifier.graphicsLayer(alpha = alpha.value)
        )
    }
}
