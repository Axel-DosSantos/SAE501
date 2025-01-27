package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val emailInput: String, val passwordInput: String)
data class LoginResponse(
    val message: String,
    val token: String,
    val userId: String
)

interface ApiService {
    @POST("connexion")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}