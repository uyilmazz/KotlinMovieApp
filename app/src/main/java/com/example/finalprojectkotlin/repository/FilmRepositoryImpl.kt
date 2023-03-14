package com.example.finalprojectkotlin.repository

import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.service.FilmAPI
import com.example.finalprojectkotlin.util.Constants
import com.example.finalprojectkotlin.util.Resource

class FilmRepositoryImpl(private val filmApi : FilmAPI) : FilmRepository {

    override suspend fun getData(): Resource<List<Film>> {
        return try{
            val response = filmApi.getData(Constants.API_KEY)
//            val response = filmApi.getDataTest()
            if(response.isSuccessful){
                response.body()?.let {
                    println(it.films?.get(0)?.filmImageUrl)
                    return Resource.success(it.films)
                } ?: Resource.error("Sunucudan cevap gelmedi",null)
            }else{
                Resource.error("İnternetinizi kontrol edin",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("Data komple yok",null)
        }
    }

    override suspend fun getDataByName(apiKey : String,filmName: String): Resource<List<Film>> {
        return try{
            val response = filmApi.getDataByName(apiKey,filmName)
            if(response.isSuccessful){
                response.body()?.let {
                    if(!response.body()!!.films.isNullOrEmpty()){
                        return Resource.success(it.films)
                    }
                    return Resource.error("${filmName} isimli film bulunamadı",null)
                } ?: Resource.error("Sunucudan cevap gelmedi",null)
            }else{
                Resource.error("İnternetinizi kontrol edin",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("Data komple yok",null)
        }
    }

    override suspend fun getDataById(apiKey : String,imdbId: String): Resource<Film> {
        return try{
            val response = filmApi.getDataById(apiKey,imdbId)
            println(response.toString())
            if(response.isSuccessful){
                response.body()?.let {
                    return Resource.success(it)
                } ?: Resource.error("Sunucudan cevap gelmedi",null)
            }else{
                Resource.error("İnternetinizi kontrol edin",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("Data komple yok",null)
        }
    }
}