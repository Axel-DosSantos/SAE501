// ProteinsResponse.kt
package com.example.myapplication.network

import com.example.myapplication.model.FoodItem
import com.google.gson.annotations.SerializedName

data class ProteinsResponse(
    @SerializedName("data") val openFoodFacts: List<FoodItem>?
)
