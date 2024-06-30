package com.example.movizy.ViewModels

import com.example.movizy.Utils.RetrofitInstance
import com.example.movizy.models.MovieDetails
import com.example.movizy.models.MovieList
import retrofit2.Response

class Repository {
    suspend fun getMovieList(): Response<MovieList>{
        return RetrofitInstance.api.getMovies()
    }

    suspend fun getUpComingMovies(): Response<MovieList>{
        return RetrofitInstance.api.getUpComingMovies()
    }

    suspend fun getTopRatedMovies(): Response<MovieList>{
        return RetrofitInstance.api.getTopRatedMovies()
    }

    suspend fun getDetailsById(id:Int): Response<MovieDetails>{
        return RetrofitInstance.api.getMovieDetailsById(id = id)
    }
}