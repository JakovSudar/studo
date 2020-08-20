package com.example.studo.data.model

data class User (
    val email : String,
    val name : String,
    val type : String,
    val verified : Int,
    val id : Int,
    val accessToken: String
)