package com.example.httpp2;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import kotlinx.coroutines.*;


public interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

}
