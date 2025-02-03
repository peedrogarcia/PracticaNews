package com.example.httpp2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpp2.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel : ViewModel() {

    private val apiService: ApiService

    init {
        // Configuración de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    // Función para obtener los titulares
    fun fetchTopHeadlines() {
        viewModelScope.launch {
            try {
                val response = apiService.getNews(
                    country = "us",
                    category = "general",
                    query = null,
                    pageSize = 20,
                    page = 1,
                    apiKey = "TU_API_KEY"
                )

                if (response.isSuccessful) {
                    response.body()?.articles?.forEach {
                        println("Título: ${it.title}")
                    }
                } else {
                    println("Error en la respuesta: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                println("Error al obtener las noticias: ${e.message}")
            }
        }
    }
}