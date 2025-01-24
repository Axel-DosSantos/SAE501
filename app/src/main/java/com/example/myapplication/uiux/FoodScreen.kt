package com.example.myapplication.uiux

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.myapplication.model.FoodCategoryItem
import com.example.myapplication.model.FoodItem
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.FoodCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCategoriesScreen(
    navController: NavHostController,
    viewModel: FoodCategoriesScreenViewModel = viewModel() // Utilisation de viewModel() pour initialisation correcte
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val foodItems by viewModel.foodItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catégories Alimentaires") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEA5F),
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Affichage des catégories sous forme de LazyRow avec LazyVerticalGrid à l'intérieur
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.getAllCategories().chunked(2)) { categoryRow ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        categoryRow.forEach { categoryItem ->
                            CategoryCard(
                                categoryItem = categoryItem,
                                onClick = { viewModel.loadFoodItems(categoryItem.category) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage des éléments de la catégorie sélectionnée
            selectedCategory?.let { _ ->
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color(0xFFFFEA5F)
                        )
                    }
                    error != null -> {
                        Text(
                            text = error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    foodItems.isEmpty() -> {
                        Text(
                            text = "Aucun élément disponible",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(foodItems) { foodItem ->
                                FoodItemDetailCard(foodItem)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodItemDetailCard(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            foodItem.imageUrl?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(foodItem.product_name, maxLines = 2)
        }
    }
}

@Composable
fun CategoryCard(
    categoryItem: FoodCategoryItem,
    onClick: () -> Unit
) {
    val categoryColors = mapOf(
        FoodCategory.PROTEINS to Color(0xFFFF6B6B),
        FoodCategory.DAIRY to Color(0xFF4ECDC4),
        FoodCategory.SWEET_PRODUCTS to Color(0xFFF9D5E5),
        FoodCategory.FATS to Color(0xFFFFA07A),
        FoodCategory.STARCHY_FOODS to Color(0xFF845EC2),
        FoodCategory.CONDIMENTS to Color(0xFFFFD700),
        FoodCategory.BEVERAGES to Color(0xFF4B9CD3)
    )

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = categoryColors[categoryItem.category] ?: Color(0xFFF0F0F0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryItem.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
