package com.practice.playoassignment.repository

import com.practice.playoassignment.api.ApiHub
import com.practice.playoassignment.model.NewsResponse
import com.practice.playoassignment.utils.Resources
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiHub: ApiHub) {
    suspend fun getNews(): Resources<NewsResponse> {
        val response = try {
            apiHub.getNews()
        } catch (e: Exception) {
            return Resources.Error("Error : ${e.message}")
        }
        return Resources.Success(response.body()!!)
    }
}