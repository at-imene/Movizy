package com.example.movizy.domain

import com.example.movizy.models.MovieDetails
import com.example.movizy.models.MovieItem
import com.example.movizy.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieList>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(): Response<MovieList>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id")id: Int
    ):Response<MovieDetails>
}