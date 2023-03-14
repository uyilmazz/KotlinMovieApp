package com.example.finalprojectkotlin.model

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("imdbID")
    val filmId : String,
    @SerializedName("Title")
    val filmName : String?,
    @SerializedName("Year")
    val filmYear : String?,
    @SerializedName("Actors")
    val filmActors : String?,
    @SerializedName("Country")
    val filmCountry : String?,
    @SerializedName("Director")
    val filmDirector : String?,
    @SerializedName("imdbRating")
    val filmImdbRating : String?,
    @SerializedName("Poster")
    val filmImageUrl : String?
)
