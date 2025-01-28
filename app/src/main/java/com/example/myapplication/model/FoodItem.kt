// FoodItem.kt
package com.example.myapplication.model

data class FoodItem(
    val product_name: String = "", // Valeur par défaut
    val imageUrl: String? = null,
    val selected_images: SelectedImages? = null,
    val nutriscore_data: NutriscoreData? = null
)

data class SelectedImages(
    val front: Display? = null
)

data class Display(
    val display: Map<String, String>? = null
)

data class NutriscoreData(
    val components: Components? = null
)



data class Components(
    val positive: List<PositiveComponent>? = null,
    val negative: List<NegativeComponent>? = null
)

data class PositiveComponent(
    val id: String = "",
    val value: Double = 0.0
)
class NegativeComponent (
    val id: String = "",
    val value: Double = 0.0
)