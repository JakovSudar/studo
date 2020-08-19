package com.example.studo.data.model.request

data class UserDataRequest (val email: String, val password: String, val password_confirmation: String?, val name: String?, val type: String?)