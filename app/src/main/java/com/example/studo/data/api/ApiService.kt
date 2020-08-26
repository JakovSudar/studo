package com.example.studo.data.api

import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.data.model.request.JobApplicationRequest
import com.example.studo.data.model.request.NewJobRequest
import com.example.studo.data.model.request.UserDataRequest
import com.example.studo.data.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("jobs")
    fun getJobs(): Call<JobsResponse>

    @POST("login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @POST("logout")
    fun logOut(@Header("Authorization")Authorization: String): Call<String>

    @POST("users/{userId}/jobApplications")
    fun applyToJob(@Body jobApplicationRequest: JobApplicationRequest, @Path("userId") userId: Int) : Call<JobApplicationRequest>

    @Headers("Accept: application/json")
    @POST("users")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>

    @GET("user")
    fun whoIsLogged(@Header("Authorization") Authorization: String): Call<User>

    @POST("users/{userId}/jobs")
    fun postNewJob(@Body newJobRequest: Job, @Path("userId") userId: Int) :Call<Job>

    @GET("users/{userId}/jobApplications")
    fun getAppliedJobs(@Path("userId") userId: Int) : Call<List<ApplicationResponse>>

    @GET("users/{userId}/jobs")
    fun getPostedJobs(@Path("userId") userId: Int) : Call<JobsResponse>

    @DELETE("users/{userId}/jobs/{jobId}")
    fun deleteJob(@Path("userId") userId: Int, @Path("jobId") jobId: Int) : Call<Job>

    @DELETE ("users/{userId}/jobApplications/{applicationId}")
    fun cancelApplication(@Path("userId") userId: Int, @Path("applicationId") applicationId: Int) : Call<ApplicationResponse>

}