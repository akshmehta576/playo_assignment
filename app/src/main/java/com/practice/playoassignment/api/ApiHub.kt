package com.practice.playoassignment.api

import com.practice.playoassignment.model.NewsResponse
import com.practice.playoassignment.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHub {

    @GET("v2/top-headlines?sources=techcrunch&apiKey=${API_KEY}")
    suspend fun getNews(): Response<NewsResponse>

}