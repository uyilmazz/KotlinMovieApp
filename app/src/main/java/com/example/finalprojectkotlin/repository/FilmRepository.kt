package com.example.finalprojectkotlin.repository

import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.util.Resource

interface FilmRepository {

    suspend fun getData() : Resource<List<Film>>
    suspend fun getDataByName(apiKey : String,filmName : String) : Resource<List<Film>>
    suspend fun getDataById(apiKey : String,mdbId : String) : Resource<Film>
}