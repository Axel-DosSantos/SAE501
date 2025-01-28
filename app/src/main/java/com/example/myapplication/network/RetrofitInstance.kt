import android.util.Log
import com.example.myapplication.network.FoodApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// RetrofitInstance.kt
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val urlWithConsumerKey = originalRequest.url.newBuilder()
                .addQueryParameter("oauth_consumer_key", "34865def945d4239b98e4f481cd40cbc")  // Remplacez par votre clé réelle
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(urlWithConsumerKey)
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
    }

    val api: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }
}


