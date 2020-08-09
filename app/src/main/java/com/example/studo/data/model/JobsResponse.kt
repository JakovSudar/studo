package com.example.studo.data.model

import com.google.gson.annotations.SerializedName

data class JobsResponse (
    @SerializedName("data")
    val jobs:List<Job>
)