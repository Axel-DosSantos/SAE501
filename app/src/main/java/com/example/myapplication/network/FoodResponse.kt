package com.example.myapplication.network

import com.example.myapplication.model.FoodItem
import com.google.gson.annotations.SerializedName

// Dans le fichier de modèle réseau
data class FoodResponse(
    @SerializedName("data") val openFoodFacts: List<FoodItem>
)
