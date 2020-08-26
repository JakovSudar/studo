package com.example.studo.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studo.data.api.Networking
import com.example.studo.data.model.request.JobApplicationRequest
import com.example.studo.helpers.PreferenceManager
import com.example.studo.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobApplyViewModel : ViewModel() {
    var aboutMe = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var jobId : Int = -1

    var step = MutableLiveData<Int>()
    var applicationResponse = MutableLiveData<Resource<JobApplicationRequest>>()


    fun applyToJob(){
        val userId = PreferenceManager().retriveUser().id
        applicationResponse.postValue(Resource.loading(null))
        Networking.apiService.applyToJob(JobApplicationRequest(aboutMe.value.toString(),jobId,phone.value.toString(),email.value.toString()),userId).enqueue(object : Callback<JobApplicationRequest>{
            override fun onFailure(call: Call<JobApplicationRequest>, t: Throwable) {
                applicationResponse.postValue(Resource.error(t.message.toString(),null))
            }
            override fun onResponse(
                call: Call<JobApplicationRequest>,
                response: Response<JobApplicationRequest>
            ) {
                applicationResponse.postValue(Resource.success(response.body()))
            }
        })
    }
}