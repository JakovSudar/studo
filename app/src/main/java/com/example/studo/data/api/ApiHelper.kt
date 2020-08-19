package com.example.studo.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getJobs() = apiService.getJobs()
}