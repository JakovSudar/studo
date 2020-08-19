package com.example.studo.data.model.response

import com.example.studo.data.model.User
import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("data")
    val user:User
)

