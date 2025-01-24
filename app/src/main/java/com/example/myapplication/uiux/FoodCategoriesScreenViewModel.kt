package com.example.myapplication.uiux

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.FoodCategory
import com.example.myapplication.model.FoodCategoryItem
import com.example.myapplication.model.FoodItem
import com.example.myapplication.network.RetrofitInstance
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

    // Liste des catégories disponibles
    private val categories = listOf(
        FoodCategoryItem(FoodCategory.PROTEINS, "Protéines"),
        FoodCategoryItem(FoodCategory.DAIRY, "Produits Laitiers"),
        FoodCategoryItem(FoodCategory.SWEET_PRODUCTS, "Produits Sucrés"),
        FoodCategoryItem(FoodCategory.FATS, "Matières Grasses"),
        FoodCategoryItem(FoodCategory.STARCHY_FOODS, "Féculents"),
        FoodCategoryItem(FoodCategory.CONDIMENTS, "Condiments"),
        FoodCategoryItem(FoodCategory.BEVERAGES, "Boissons")
    )

    // Fonction pour charger les éléments en fonction de la catégorie sélectionnée
    fun loadFoodItems(category: FoodCategory) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _selectedCategory.value = category

                // Récupérer les données via Retrofit selon la catégorie
                val response = when (category) {
                    FoodCategory.PROTEINS -> RetrofitInstance.api.getProteins()
                    FoodCategory.DAIRY -> RetrofitInstance.api.getDairyProducts()
                    FoodCategory.SWEET_PRODUCTS -> RetrofitInstance.api.getSweetProducts()
                    FoodCategory.FATS -> RetrofitInstance.api.getFatsProducts()
                    FoodCategory.STARCHY_FOODS -> RetrofitInstance.api.getStarchyFoods()
                    FoodCategory.CONDIMENTS -> RetrofitInstance.api.getCondiments()
                    FoodCategory.BEVERAGES -> RetrofitInstance.api.getBeverages()
                }

                // Aplatir la liste des éléments et gérer les images
                val flattenedList = response.openFoodFacts?.map { item ->
                    item.copy(imageUrl = item.selected_images?.front?.display?.get("fr"))
                } ?: emptyList()
                _foodItems.value = flattenedList

            } catch (e: Exception) {
                Log.e("FoodCategoriesScreen", "Error loading food items: ${e.message}", e)
                _error.value = "Erreur lors du chargement des données: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Retourne toutes les catégories
    fun getAllCategories() = categories
}
