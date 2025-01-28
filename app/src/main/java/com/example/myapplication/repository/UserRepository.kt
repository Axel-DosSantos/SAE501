package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.api.ApiService
import com.example.myapplication.api.LoginRequest
import com.example.myapplication.api.LoginResponse
import com.example.myapplication.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val apiService: ApiService = RetrofitClient.instance

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        val request = LoginRequest(emailInput = email, passwordInput = password)
        apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("UserRepository", "Login successful: ${loginResponse?.message}, UserId: ${loginResponse?.userId}")
                    onResult(true, loginResponse?.token)
                } else {
                    Log.e("UserRepository", "Login failed: ${response.errorBody()?.string()}")
                    onResult(false, null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("UserRepository", "Login error", t)
                onResult(false, null)
            }
        })
    }
}