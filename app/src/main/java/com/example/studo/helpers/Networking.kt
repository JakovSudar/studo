package com.example.studo.helpers

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.studo.data.api.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking{
    val apiService: ApiService = Retrofit.Builder()
        .addConverterFactory(ConverterFactory.converterFactory)
        .client(HttpClient.client)
        .baseUrl("http://192.168.137.1:8000/api/")
        .build()
        .create(ApiService::class.java)
}

object ConverterFactory{
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val converterFactory = GsonConverterFactory.create(gson)
}

object HttpClient{
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}
