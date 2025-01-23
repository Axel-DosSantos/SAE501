package com.example.myapplication.network

import com.example.myapplication.model.FoodItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Classe pour gérer la réponse de l'API
data class ProteinsResponse(
    val data: List<FoodItem>
) : List<FoodItem> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: FoodItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<FoodItem>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): FoodItem {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: FoodItem): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<FoodItem> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: FoodItem): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<FoodItem> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<FoodItem> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<FoodItem> {
        TODO("Not yet implemented")
    }
}

// Interface de l'API
interface FoodApiService {
    // Requête GET pour récupérer les protéines
    @GET("proteines/allProteines")
    suspend fun getProteins(): ProteinsResponse
}

// Instance Retrofit
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3000/"  // Remplacez par l'URL de votre API

    // Instance de Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Service API
    val api: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }
}