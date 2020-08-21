package com.example.studo.data.model.request

data class NewJobRequest (val title: String, val description: String, val category: String, val city: String, val wage: Double, val employer: String, val requirements: String)