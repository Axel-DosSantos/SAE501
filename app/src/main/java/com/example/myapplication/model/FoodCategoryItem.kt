package com.example.myapplication.model  // Le même package que les autres modèles

import com.example.myapplication.model.FoodCategory

// Classe représentant un élément de catégorie alimentaire.
data class FoodCategoryItem(

    val category: FoodCategory,   // Catégorie d'aliment (ex : Protéines, Produits Laitiers, etc.)
    val title: String,            // Le titre à afficher pour cette catégorie (ex : "Protéines")
    val icon: String? = null      // Une icône optionnelle pour la catégorie (ex : une icône d'image)
)
