package com.example.studo.data.api

import com.example.studo.data.model.Job
import com.example.studo.data.model.JobsResponse
import io.reactivex.Single

interface ApiService {
    fun getJobs(): Single<JobsResponse>
    fun getJob(id: Int): Single<Job>

}