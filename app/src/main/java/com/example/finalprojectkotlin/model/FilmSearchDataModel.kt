package com.example.finalprojectkotlin.model

import com.google.gson.annotations.SerializedName

data class FilmSearchDataModel(
    @SerializedName("Search")
    val films : List<Film>?,
    @SerializedName("totalResults")
    val total : String?,
    @SerializedName("Response")
    val response : String?
)