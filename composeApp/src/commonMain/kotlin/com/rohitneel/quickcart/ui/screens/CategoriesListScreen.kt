package com.rohitneel.quickcart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rohitneel.quickcart.data.GroceryRepository
import com.rohitneel.quickcart.ui.components.CategoryTile
import com.rohitneel.quickcart.ui.theme.BrandColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesListScreen(
    onCategoryClick: (String) -> Unit,
) {
    val categories = remember { GroceryRepository.getCategories() }
    
    val sections = remember(categories) {
        val groceryAndKitchenIds = listOf("fruits_veg", "atta_rice_dal", "masala_oil", "dairy_bread", "bakery", "meat_fish")
        val snacksAndDrinksIds = listOf("snacks", "beverages", "tea_coffee", "sweet_namkeen", "frozen")

        listOf(
            "Grocery & Kitchen" to categories.filter { it.id in groceryAndKitchenIds },
            "Snacks & Drinks" to categories.filter { it.id in snacksAndDrinksIds },
            "Household & More" to categories.filter { it.id !in groceryAndKitchenIds && it.id !in snacksAndDrinksIds }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Categories",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandColors.Surface)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BrandColors.Surface),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            sections.forEach { (title, sectionCategories) ->
                item(
                    span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) },
                    contentType = "header"
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
                items(
                    items = sectionCategories,
                    key = { it.id },
                    contentType = { "category" }
                ) { category ->
                    CategoryTile(
                        category = category,
                        onClick = { onCategoryClick(category.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item(
                span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) },
                contentType = "spacer"
            ) {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
