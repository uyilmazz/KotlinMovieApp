package com.example.finalprojectkotlin.di

import com.example.finalprojectkotlin.repository.FilmRepository
import com.example.finalprojectkotlin.repository.FilmRepositoryImpl
import com.example.finalprojectkotlin.service.FilmAPI
import com.example.finalprojectkotlin.util.Constants
import com.example.finalprojectkotlin.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmAPI::class.java)
    }

    single<FilmRepository> {
        FilmRepositoryImpl(get())
    }

    viewModel {
        FilmViewModel(get())
    }

}