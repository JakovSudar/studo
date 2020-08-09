package com.example.studo.data.model
import com.google.gson.annotations.SerializedName

data class Job (
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String="",
    @SerializedName("description")
    val description: String="",
    @SerializedName("category")
    val category: String ="",
    @SerializedName("city")
    val city: String ="",
    @SerializedName("wage")
    val wage: Double =0.0,
    @SerializedName("requirements")
    val requirements: String="",
    @SerializedName("employer")
    val employer: String = "",
    @SerializedName("notes")
    val notes: String =""
)