package com.example.myapplication.uiux

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.FoodCategory
import com.example.myapplication.model.FoodItem
import com.example.myapplication.network.ProteinsResponse
import com.example.myapplication.network.FoodResponse
import RetrofitInstance
import com.example.myapplication.model.FoodCategoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodCategoriesScreenViewModel : ViewModel() {
    private val _selectedCategory = MutableStateFlow<FoodCategory?>(null)
    val selectedCategory: StateFlow<FoodCategory?> = _selectedCategory.asStateFlow()

    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val categories = listOf(
        FoodCategoryItem(FoodCategory.PROTEINS, "Protéines"),
        FoodCategoryItem(FoodCategory.DAIRY, "Produits Laitiers"),
        FoodCategoryItem(FoodCategory.SWEET_PRODUCTS, "Produits Sucrés"),
        FoodCategoryItem(FoodCategory.FATS, "Matières Grasses"),
        FoodCategoryItem(FoodCategory.STARCHY_FOODS, "Féculents"),
        FoodCategoryItem(FoodCategory.CONDIMENTS, "Condiments"),
        FoodCategoryItem(FoodCategory.BEVERAGES, "Boissons")
    )

    fun loadFoodItems(category: FoodCategory) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _selectedCategory.value = category

                // Appel API en fonction de la catégorie
                val foodItemsList = when (category) {
                    FoodCategory.PROTEINS -> {
                        // Assurer que nous avons bien une liste valide
                        val response = RetrofitInstance.api.getProteins()
                        response.openFoodFacts ?: emptyList() // Utilisation correcte sans 'flatten'
                    }
                    FoodCategory.DAIRY -> RetrofitInstance.api.getDairyProducts()
                    FoodCategory.SWEET_PRODUCTS -> RetrofitInstance.api.getSweetProducts()
                    FoodCategory.FATS -> RetrofitInstance.api.getFatsProducts()
                    FoodCategory.STARCHY_FOODS -> RetrofitInstance.api.getStarchyFoods()
                    FoodCategory.CONDIMENTS -> RetrofitInstance.api.getCondiments()
                    FoodCategory.BEVERAGES -> RetrofitInstance.api.getBeverages()
                    else -> emptyList()
                }

                // Traitement des éléments reçus
                _foodItems.value = foodItemsList.mapNotNull { item ->
                    if (item.product_name.isNullOrBlank()) {
                        null
                    } else {
                        item.copy(
                            imageUrl = item.selected_images?.front?.display?.get("fr"),
                            product_name = item.product_name.trim()
                        )
                    }
                }

                // Vérifier si la liste est vide
                if (_foodItems.value.isEmpty()) {
                    _error.value = "Aucun produit trouvé pour cette catégorie."
                }

            } catch (e: Exception) {
                Log.e("FoodCategoriesScreen", "Error loading food items: ${e.message}", e)
                _error.value = "Erreur lors du chargement des données: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllCategories() = categories
}
