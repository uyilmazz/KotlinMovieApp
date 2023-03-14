package com.example.finalprojectkotlin.service

import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.model.FilmSearchDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmAPI {

    @GET("?s=Game%20of%20Thrones&Season=1")
    suspend fun getData(@Query("apikey") apiKey : String) : Response<FilmSearchDataModel>

    @GET(".")
    suspend fun getDataByName(@Query("apikey") apiKey: String,@Query("s") filmName : String) : Response<FilmSearchDataModel>

    @GET(".")
    suspend fun getDataById(@Query("apikey") apiKey: String,@Query("i") id : String) : Response<Film>
}