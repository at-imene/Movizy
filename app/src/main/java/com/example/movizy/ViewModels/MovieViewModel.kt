package com.example.movizy.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movizy.models.MovieDetails
import com.example.movizy.models.MovieItem
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = Repository()
    var state by mutableStateOf(ScreenState())

    var id by mutableIntStateOf(0)
    var isFavorite by mutableStateOf(false)

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                var response = repository.getMovieList()
                if (response.isSuccessful) {
                    response.body().let {
                        state = state.copy(
                            movies = response.body()!!.results
                        )
                    }

                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message
                )
            }
        }
        viewModelScope.launch {
            try {
                var response = repository.getUpComingMovies()
                if (response.isSuccessful) {
                    response.body().let {
                        state = state.copy(
                            upComingMovies = response.body()!!.results
                        )
                    }
                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message
                )
            }
        }

        viewModelScope.launch {
            try {
                var response = repository.getTopRatedMovies()
                if (response.isSuccessful) {
                    response.body().let {
                        state = state.copy(
                            topRatedMovies = response.body()!!.results
                        )
                    }

                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.message
                )
            }
        }
    }

    fun loadMovieDetails() {
        if (id > 0) {
            viewModelScope.launch {
                try {
                    var response = repository.getDetailsById(id)
                    if (response.isSuccessful) {
                        state = state.copy(
                            movieDetails = response.body()!!
                        )
                    }
                } catch (e: Exception) {
                    state = state.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun toggleFavorite(){
        isFavorite = !isFavorite
    }
}


data class ScreenState(
    val movies: List<MovieItem> = emptyList(),
    val upComingMovies: List<MovieItem> = emptyList(),
    val topRatedMovies: List<MovieItem> = emptyList(),
    val movieDetails: MovieDetails = MovieDetails(),
    var isFavorite: Boolean = false,
    val error: String? = "",
)