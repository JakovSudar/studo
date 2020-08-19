package com.example.studo.data.api

import com.example.studo.data.model.AuthResponse
import com.example.studo.data.model.Job
import com.example.studo.data.model.JobsResponse
import com.example.studo.data.model.request.UserDataRequest
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("jobs")
    fun getJobs(): Call<JobsResponse>

    @POST("login")
    fun login(@Body userData: UserDataRequest): Call<AuthResponse>
}