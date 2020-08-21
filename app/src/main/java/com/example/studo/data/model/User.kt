package com.example.studo.data.model

data class User (
    val email : String,
    var name : String,
    var type : String,
    val verified : Int,
    var id : Int,
    val accessToken: String
)