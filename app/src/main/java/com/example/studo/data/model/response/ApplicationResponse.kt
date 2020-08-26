package com.example.studo.data.model.response

import com.example.studo.data.model.Job
import com.google.gson.annotations.SerializedName

data class ApplicationResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("job_id") val job_id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("about_me") val about_me : String,
    @SerializedName("job") val job : Job

)
