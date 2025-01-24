package com.example.myapplication.model

data class FoodItem(
    val product_name: String,
    val imageUrl: String?,
    val selected_images: SelectedImages?,
    val nutriscore_data: NutriscoreData?
)

data class SelectedImages(
    val front: Display?
)

data class Display(
    val display: Map<String, String>?
)

data class NutriscoreData(
    val components: Components?
)

data class Components(
    val positive: List<PositiveComponent>?
)

data class PositiveComponent(
    val id: String,
    val value: Double
)

