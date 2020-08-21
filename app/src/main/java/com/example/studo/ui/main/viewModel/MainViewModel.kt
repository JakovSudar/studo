package com.example.studo.ui.main.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studo.helpers.Networking
import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.data.model.response.JobsResponse
import com.example.studo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() :ViewModel(){
    private var jobs = MutableLiveData<Resource<List<Job>>>()
    private lateinit var user: User
    var jobsList = mutableListOf<Job>()


    var job = MutableLiveData<Job>()

    init {
        fetchJobs()
    }

    fun getJob(jobId:Int){
        val chosenJob = jobsList.filter { job -> job.id == jobId }.single()
        Log.d("jobsList:", chosenJob.toString())
        this.job.postValue(chosenJob)
    }

    private fun fetchJobs() {
        jobs.postValue(Resource.loading(null))

        Networking.apiService.getJobs().enqueue(
            object : Callback<JobsResponse> {
                override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                    jobs.postValue(Resource.error("Something went wrong",null))
                }

                override fun onResponse(
                    call: Call<JobsResponse>,
                    response: Response<JobsResponse>
                ) {
                    jobs.postValue(Resource.success(response.body()?.jobs))
                    jobsList= response.body()?.jobs as MutableList<Job>
                }
            }
        )
    }

    fun postNewJob(job:Job){
        Networking.apiService.postNewJob(job,user.id).enqueue(object : Callback<Job>{
            override fun onFailure(call: Call<Job>, t: Throwable) {
                Log.d("NewJob response",t.message)
            }

            override fun onResponse(call: Call<Job>, response: Response<Job>) {
                Log.d("NewJob response",response.body().toString())
            }

        })
    }

    fun getJobs(): LiveData<Resource<List<Job>>>{
        return jobs
    }

    fun setUser(user: User){
        this.user = user
    }






}