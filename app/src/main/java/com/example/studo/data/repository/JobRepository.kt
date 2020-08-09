package com.example.studo.data.repository

import android.util.Log
import com.example.studo.data.api.ApiHelper
import com.example.studo.data.model.Job
import com.example.studo.data.model.JobsResponse
import io.reactivex.Single

class JobRepository(private val apiHelper: ApiHelper) {
    fun getJobs(): Single<JobsResponse>{
        return apiHelper.getJobs()
    }

    fun getJob(id: Int): Single<Job>{
        return apiHelper.getJob(id)
    }
}