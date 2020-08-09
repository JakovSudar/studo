package com.example.studo.ui.main.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studo.data.model.Job
import com.example.studo.data.repository.JobRepository
import com.example.studo.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val jobRepository: JobRepository) :ViewModel(){
    private var jobs = MutableLiveData<Resource<List<Job>>>()
    private var jobsList = mutableListOf<Job>()
    var job = MutableLiveData<Job>()
    private val compositeDisposable = CompositeDisposable()

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
        compositeDisposable.add(
            jobRepository.getJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    jobsResponse ->
                    run { jobs.postValue(Resource.success(jobsResponse.jobs))
                            this.jobsList= jobsResponse.jobs as MutableList<Job>
                    }
                },{throwable ->
                    jobs.postValue(Resource.error("Something went wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getJobs(): LiveData<Resource<List<Job>>>{
        return jobs
    }






}