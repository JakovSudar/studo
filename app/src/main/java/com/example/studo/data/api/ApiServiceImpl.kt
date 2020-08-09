package com.example.studo.data.api

import android.util.Log
import com.example.studo.data.model.Job
import com.example.studo.data.model.JobsResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl: ApiService {
    override fun getJobs(): Single<JobsResponse> {
        val response = Rx2AndroidNetworking.get("http://192.168.137.1:8000/api/jobs")
            .build()
            .getObjectSingle(JobsResponse::class.java)
        return response
    }

    override fun getJob(id: Int): Single<Job> {
        val response = Rx2AndroidNetworking.get("http://192.168.137.1:8000/api/jobs/$id")
            .build()
            .getObjectSingle(Job::class.java)
        return response
    }

    //

}