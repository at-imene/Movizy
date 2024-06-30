package com.example.movizy.models

data class MovieItem(
     val adult: Boolean = false,
     val id: Int = 0,
     val original_language: String = "",
     val overview: String = "",
     val poster_path: String ="",
     val release_date: String ="",
     val title: String ="",
     val vote_average: Double = 0.0,
     val vote_count: Int = 0)
