package com.example.httpp2

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.httpp2.ApiService
import com.example.httpp2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.View
import android.widget.AdapterView



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var NewsAdapter: NewsAdapter
    private var selectedCategory: String = "general"  // Categoría por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustes de paddings para status bar y navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()

        NewsAdapter = NewsAdapter(emptyList())
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = NewsAdapter
        }

        setupCategorySpinner()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    // spinner de categorias
    private fun setupCategorySpinner() {
        val categories = resources.getStringArray(R.array.categories_array)

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = when (categories[position]) {
                    "Negocios" -> "business"
                    "Entretenimiento" -> "entertainment"
                    "Salud" -> "health"
                    "Ciencia" -> "science"
                    "Deportes" -> "sports"
                    "Tecnología" -> "technology"
                    else -> "general"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se hace nada si no se selecciona nada
            }
        }
    }

    private fun searchByName(query: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = retrofit.create(ApiService::class.java)

                val searchQuery = if (query.isNullOrBlank()) null else query

                val response = apiService.getNews(
                    country = "us",
                    category = selectedCategory,
                    query = searchQuery,
                    pageSize = 20,
                    page = 1,
                    apiKey = "a359c6caa0f142adaba76808554ae804"
                )

                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    val articles = newsResponse?.articles ?: emptyList()

                    if (articles.isNotEmpty()) {
                        Log.d("MainActivity", "Primer artículo: ${articles[0].title}")
                        runOnUiThread {
                            NewsAdapter.updateData(articles)
                        }
                    } else {
                        Log.d("MainActivity", "La lista de artículos está vacía.")
                    }
                } else {
                    Log.e("MainActivity", "Error en la respuesta: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Excepción en la petición: ${e.message}")
            }
        }
    }

    private val retrofit: Retrofit by lazy {

        // 1. Creamos el interceptor y establecemos el nivel de log
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            // Los niveles disponibles son: NONE, BASIC, HEADERS, BODY
            // BODY te muestra absolutamente todo (peticiones y respuestas).
        }

        // 2. Construimos el OkHttpClient y le agregamos el interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // 3. Construimos Retrofit usando ese cliente
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // <--- Aquí se asocia con Retrofit
            .build()
    }

    //private fun getRetrofit(): Retrofit {
    //    return Retrofit.Builder()
    //        .baseUrl("https://newsapi.org/v2/")
    //        .addConverterFactory(GsonConverterFactory.create())
    //        .build()
    //}
}
