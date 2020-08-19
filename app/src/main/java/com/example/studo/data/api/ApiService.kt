package com.example.studo.data.api

import com.example.studo.data.model.User
import com.example.studo.data.model.response.LoginResponse
import com.example.studo.data.model.response.JobsResponse
import com.example.studo.data.model.request.UserDataRequest
import com.example.studo.data.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @GET("jobs")
    fun getJobs(): Call<JobsResponse>

    @POST("login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @Headers("Accept: application/json")
    @POST("users")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>
}