package com.example.studo.data.model.response

import com.example.studo.data.model.Job
import com.google.gson.annotations.SerializedName

data class JobsResponse (
    @SerializedName("data")
    val jobs:List<Job>
)