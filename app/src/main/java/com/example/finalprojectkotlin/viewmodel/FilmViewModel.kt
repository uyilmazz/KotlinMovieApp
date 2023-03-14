package com.example.finalprojectkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.repository.FilmRepository
import com.example.finalprojectkotlin.util.Resource
import com.example.finalprojectkotlin.util.Status
import kotlinx.coroutines.*

class FilmViewModel(private val filmRepository : FilmRepository) : ViewModel() {
    val filmList = MutableLiveData<Resource<List<Film>>>()
    val filmLoading = MutableLiveData<Resource<Boolean>>()
    val filmError = MutableLiveData<Resource<Boolean>>()
    val film = MutableLiveData<Resource<Film>>()
    val apiKey = "c122f9cc"

    private var job : Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        print("exception handler : " + throwable.localizedMessage)
        filmError.value = Resource.error(throwable.localizedMessage ?: "error",true)
    }

    fun getData(){
        filmLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = filmRepository.getData()
            withContext(Dispatchers.Main){
                resource.data?.let {
                    filmList.value = resource
                    filmLoading.value = Resource.loading(false)
                    filmError.value = Resource.error("Error",false)
                }
            }
        }
    }

    fun getDataByName(fileName : String){
        filmLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = filmRepository.getDataByName(apiKey,fileName)
            withContext(Dispatchers.Main){
                if(resource.status == Status.SUCCESS){
                    resource.data?.let {
                        filmList.value = resource
                        filmLoading.value = Resource.loading(false)
                        filmError.value = Resource.error(resource.message ?: "",false)
                    }
                }else if(resource.status == Status.ERROR){
                    filmLoading.value = Resource.loading(false)
                    filmError.value = Resource.error(resource.message ?: "",true)
                }
            }
        }
    }

    fun getDataById(imdbId : String) {
        filmLoading.value = Resource.loading(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = filmRepository.getDataById(apiKey,imdbId)
            withContext(Dispatchers.Main){
                resource.data?.let {
                    film.value = Resource.success(it)
                    filmLoading.value = Resource.loading(false)
                    filmError.value = Resource.error("Error",false)
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }

}