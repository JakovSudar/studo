package com.example.studo.ui.main.viewModel

import android.media.session.MediaSession
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studo.data.api.Networking
import com.example.studo.data.model.Job
import com.example.studo.data.model.User
import com.example.studo.data.model.request.TokenRequest
import com.example.studo.data.model.response.ApplicationResponse
import com.example.studo.data.model.response.JobsResponse
import com.example.studo.helpers.PreferenceManager
import com.example.studo.ui.auth.viewModel.EMPLOYER
import com.example.studo.ui.auth.viewModel.STUDENT
import com.example.studo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel() :ViewModel(){
    private var jobs = MutableLiveData<Resource<List<Job>>>()
    private var profileJobs = MutableLiveData<Resource<List<Job>>>()
    var deletedJob =MutableLiveData<Resource<Job>>()
    var job = MutableLiveData<Job>()
    var jobToApply = MutableLiveData<Job>()
    private lateinit var user: User
    var jobsList = mutableListOf<Job>()
    var jobApplicationsDisplay = MutableLiveData<Job>()
    var postedJob = MutableLiveData<Resource<Job>>()

    init {
        fetchJobs()
    }

    fun fetchJobs() {
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
        postedJob.postValue(Resource.loading(null))
        Networking.apiService.postNewJob(job,user.id).enqueue(object : Callback<Job>{
            override fun onFailure(call: Call<Job>, t: Throwable) {
                postedJob.postValue(Resource.error(t.message.toString(),null))
            }
            override fun onResponse(call: Call<Job>, response: Response<Job>) {
                postedJob.postValue(Resource.success(response.body()))
            }
        })
    }

    fun fetchAppliedJobs(){
        profileJobs.postValue(Resource.loading(null))
        Networking.apiService.getAppliedJobs(user.id).enqueue(object : Callback<List<ApplicationResponse>>{
            override fun onFailure(call: Call<List<ApplicationResponse>>, t: Throwable) {
                Log.d("Applications response",t.message)
                profileJobs.postValue(Resource.error("Something went wrong",null))
            }
            override fun onResponse(
                call: Call<List<ApplicationResponse>>,
                response: Response<List<ApplicationResponse>>
            ) {
                Log.d("Applications response",response.body().toString())
                var appliedJobsDetails = mutableListOf<Job>()
                response.body()?.forEach {
                    var job = it.job
                    job.applicationId = it.id
                    appliedJobsDetails.add(it.job)
                }
                profileJobs.postValue(Resource.success(appliedJobsDetails))
                Log.d("Applied jobs", appliedJobsDetails.toString())
            }
        })
    }

    fun showJobApplications(job:Job){
        this.jobApplicationsDisplay.postValue(job)
    }

    fun fetchPostedJobs(){
        profileJobs.postValue(Resource.loading(null))
        Networking.apiService.getPostedJobs(user.id).enqueue(object : Callback<JobsResponse>{
            override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                Log.d("Applications response",t.message)
                profileJobs.postValue(Resource.error("Something went wrong",null))
            }
            override fun onResponse(
                call: Call<JobsResponse>,
                response: Response<JobsResponse>
            ) {
                Log.d("Applications response",response.body().toString())
                profileJobs.postValue(Resource.success(response.body()?.jobs))
            }
        })
    }

    fun saveToken(fcmToken: String){
        Networking.apiService.sendTokenToServer(TokenRequest(fcmToken), user.id).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("TOKENRESPONSE error", t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("TOKENRESPONSE", response.body().toString())
            }
        })
    }

    fun deleteToken(){
        Networking.apiService.deleteToken(user.id).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteToken error", t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("deleteToken", "success")
            }
        })
    }

    fun deleteJob(job: Job){
        Networking.apiService.deleteJob(user.id, job.id).enqueue(object : Callback<Job>{
            override fun onFailure(call: Call<Job>, t: Throwable) {
                TODO("Javiti gresku")
            }

            override fun onResponse(call: Call<Job>, response: Response<Job>) {
                deletedJob.postValue(Resource.success(response.body()))
            }
        })
    }

    fun applyToJob(job: Job){
        this.jobToApply.postValue(job)
    }

    fun cancelApply(job: Job){
        Networking.apiService.cancelApplication(user.id, job.applicationId).enqueue(object :Callback<ApplicationResponse>{
            override fun onFailure(call: Call<ApplicationResponse>, t: Throwable) {
                TODO("Javiti gresku")
            }
            override fun onResponse(
                call: Call<ApplicationResponse>,
                response: Response<ApplicationResponse>
            ) {
                deletedJob.postValue(Resource.success(job))
            }
        })
    }

    fun getJob(job: Job){
        this.job.postValue(job)
    }

    fun getProfileJobs(): LiveData<Resource<List<Job>>>{
        return profileJobs
    }

    fun deletedJob(): LiveData<Resource<Job>>{
        return deletedJob
    }

    fun getJobs(): LiveData<Resource<List<Job>>>{
        return jobs
    }
    fun hasToken():Boolean{
        return user.accessToken != PreferenceManager.DEF_VAL
    }

    fun setUser(user: User){
        this.user = user
    }

    fun isStudent(): Boolean{
        return user.type == STUDENT
    }

    fun getUser(): User{
        return user
    }






}