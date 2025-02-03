package com.example.httpp2

// Clase de respuesta de la API
data class NewsResponse(
    val status: String,                 // Estado de la respuesta (ej. "ok")
    val totalResults: Int,              // Número total de resultados
    val articles: List<Article>         // Lista de artículos
)

// Clase para los artículos
data class Article(
    val source: Source,                 // Fuente del artículo
    val author: String?,                // Autor del artículo (puede ser nulo)
    val title: String,                  // Título de la noticia
    val description: String?,           // Descripción o snippet
    val url: String,                    // URL al artículo completo
    val urlToImage: String?,            // URL de la imagen asociada
    val publishedAt: String,            // Fecha de publicación
    val content: String?                // Contenido (puede estar truncado)
)

// Clase para la fuente del artículo
data class Source(
    val id: String?,                    // ID de la fuente (puede ser nulo)
    val name: String                    // Nombre de la fuente
)

