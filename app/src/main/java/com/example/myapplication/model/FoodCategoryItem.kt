package com.example.myapplication.model

data class FoodCategoryItem(
    val category: FoodCategory,
    val title: String,
    val icon: String? = null
)