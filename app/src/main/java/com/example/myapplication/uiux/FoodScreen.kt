package com.example.myapplication.uiux

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.FoodCategory
import com.example.myapplication.model.FoodCategoryItem
import com.example.myapplication.model.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(
    navController: NavHostController,
    viewModel: FoodCategoriesScreenViewModel = viewModel()
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
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp) // Ajouter de l'espace vertical entre les cartes
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
            .padding(8.dp), // Ajouter du padding pour séparer les cartes
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Afficher l'image si disponible
            foodItem.imageUrl?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Afficher le nom du produit
            Text(
                text = foodItem.product_name,
                maxLines = 2,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Afficher les valeurs nutritionnelles négatives en excluant "non_nutritive_sweeteners"
            foodItem.nutriscore_data?.components?.negative?.filter { it.id != "non_nutritive_sweeteners" }?.let { negativeComponents ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    negativeComponents.forEach { component ->
                        Text(
                            text = "${component.id.capitalize()}: ${component.value} ${component.id}",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Afficher les valeurs nutritionnelles positives en excluant "fruits_vegetables_legumes"
            foodItem.nutriscore_data?.components?.positive?.filter { it.id != "fruits_vegetables_legumes" }?.let { positiveComponents ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    positiveComponents.forEach { component ->
                        Text(
                            text = "${component.id.capitalize()}: ${component.value} ${component.id}",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
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
