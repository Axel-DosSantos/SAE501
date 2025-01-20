package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.uiux.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun MyAppNavHost(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val isFirstLaunch = remember { context.isFirstLaunch() }

    NavHost(navController, startDestination = if (isFirstLaunch) "login" else "main") {
        composable("login") { LoginPage(navController) }
        composable("questionnaire") { QuestionnaireScreen(navController) }
        composable("additionalInfo") { AdditionalInfoScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("home") { MainActivity() }
        composable("food") { FoodScreen(navController) }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
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
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedItem == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (item.title) {
                                    "Food" -> navController.navigate("food")
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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

                // Deuxième Card : Calories
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(300.dp)
                        .padding(bottom = 16.dp)
                        .offset(y = (-42).dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Calories",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Troisième Card : Add Poids
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Add Poids",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        ) {
                            IconButton(
                                onClick = { /* Action */ },
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Ajouter",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }

                // Quatrième Card : Macronutriments
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Macronutriments",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Protéines",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        LinearProgressIndicator(
                            progress = { 0.7f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Lipides",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        LinearProgressIndicator(
                            progress = { 0.5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Glucides",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        LinearProgressIndicator(
                            progress = { 0.3f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// Extension pour vérifier si c'est le premier lancement
fun Context.isFirstLaunch(): Boolean {
    val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)
    if (isFirstLaunch) {
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }
    return isFirstLaunch
}