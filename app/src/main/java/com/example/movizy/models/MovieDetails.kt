package com.example.movizy.models

data class MovieDetails(
    val id: Int = 0,
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val budget: Int = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String = "",
    val imdb_id: String  = "",
    val origin_country: List<String> = emptyList(),
    val original_language: String =  "",
    val original_title: String  = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String  = "",
    val release_date: String = "",
    val revenue: Int = 0,
    val runtime: Int =0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)