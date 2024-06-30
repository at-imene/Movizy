package com.example.movizy.models

data class MovieList (
    val results :  List<MovieItem> = emptyList(),
    val page : Int = 0
    )