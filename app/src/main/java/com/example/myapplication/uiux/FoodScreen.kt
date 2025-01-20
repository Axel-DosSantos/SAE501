package com.example.myapplication.uiux

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(navController: NavController) {
    var selectedItem by remember { mutableStateOf(1) }
    val items = listOf(
        NavigationItem("Home", Icons.Rounded.Home),
        NavigationItem("Food", Icons.Rounded.RestaurantMenu)
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(20.dp),
                        spotColor = Color.Black.copy(alpha = 0.25f)
                    )
            ) {
                BottomNavigation(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp)),
                    backgroundColor = Color(0xFFFFEA5F),
                    elevation = 0.dp
                ) {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(26.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 15.sp,
                                    fontWeight = if (selectedItem == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                if (item.title == "Home") {
                                    navController.navigate("main") {
                                        popUpTo("main") { inclusive = true }
                                    }
                                }
                            },
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.DarkGray.copy(alpha = 0.7f),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEEEEEE))
                    .padding(16.dp)
            ) {
                // Première Card : Bandeau "Hi Axel"
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp)
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEA5F))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Hi Axel",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEA5F))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Grid des catégories alimentaires
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Première colonne
                            Column(modifier = Modifier.weight(1f)) {
                                FoodCategoryCard(
                                    title = "Viandes",
                                    backgroundColor = Color(0xFFE57373),
                                    modifier = Modifier
                                        .padding(end = 8.dp, bottom = 8.dp)
                                        .height(160.dp)
                                )
                                FoodCategoryCard(
                                    title = "Légumes",
                                    backgroundColor = Color(0xFF81C784),
                                    modifier = Modifier
                                        .padding(end = 8.dp, bottom = 8.dp)
                                        .height(160.dp)
                                )
                            }

                            // Deuxième colonne
                            Column(modifier = Modifier.weight(1f)) {
                                FoodCategoryCard(
                                    title = "Épices",
                                    backgroundColor = Color(0xFFFFB74D),
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .height(160.dp)
                                )
                                FoodCategoryCard(
                                    title = "Produits laitiers",
                                    backgroundColor = Color(0xFF64B5F6),
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .height(160.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
data class NavigationItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodCategoryCard(
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        onClick = { /* Handle click */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}