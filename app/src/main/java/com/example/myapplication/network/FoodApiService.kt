package com.example.myapplication.network

import retrofit2.http.GET

interface FoodApiService {
    @GET("proteines/allProteines")
    suspend fun getProteins(): FoodResponse

    @GET("produits-laitiers/allProduitsLaitiers")
    suspend fun getDairyProducts(): FoodResponse

    @GET("produits-sucree-gras/allProduitSucree")
    suspend fun getSweetProducts(): FoodResponse

    @GET("matieres-grasses/allMatiereGrasse")
    suspend fun getFatsProducts(): FoodResponse

    @GET("feculants/allFeculant")
    suspend fun getStarchyFoods(): FoodResponse

    @GET("condiments/allCondiments")
    suspend fun getCondiments(): FoodResponse

    @GET("boissons/allBoisson")
    suspend fun getBeverages(): FoodResponse
}
