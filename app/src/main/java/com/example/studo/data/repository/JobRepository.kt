package com.example.studo.data.repository

import com.example.studo.data.api.ApiHelper
import com.example.studo.data.model.response.JobsResponse
import retrofit2.Call

class JobRepository(private val apiHelper: ApiHelper) {
    fun getJobs(): Call<JobsResponse> {
        TODO()
    }
}