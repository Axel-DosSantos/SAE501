// FoodApiService.kt
package com.example.myapplication.network

import com.example.myapplication.model.FoodItem
import retrofit2.http.GET

interface FoodApiService {
    @GET("proteines/allProteines")
    suspend fun getProteins(): ProteinsResponse  // Changé ici

    @GET("produits-laitiers/allProduitsLaitiers")
    suspend fun getDairyProducts(): List<FoodItem>

    @GET("produits-sucree-gras/allProduitSucree")
    suspend fun getSweetProducts(): List<FoodItem>

    @GET("matieres-grasses/allMatiereGrasse")
    suspend fun getFatsProducts(): List<FoodItem>

    @GET("feculants/allFeculant")
    suspend fun getStarchyFoods(): List<FoodItem>

    @GET("condiments/allCondiments")
    suspend fun getCondiments(): List<FoodItem>

    @GET("boissons/allBoisson")
    suspend fun getBeverages(): List<FoodItem>
}