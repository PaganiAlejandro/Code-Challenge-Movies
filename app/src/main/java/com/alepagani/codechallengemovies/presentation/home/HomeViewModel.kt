package com.alepagani.codechallengemovies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import com.alepagani.codechallengemovies.domain.GetMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMovieWithGenreUseCase
import com.alepagani.codechallengeyape.core.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieWithGenreUseCase: GetMovieWithGenreUseCase,
    private val getMovieLikedUseCase: GetMovieLikedUseCase
) : ViewModel() {

    private val _moviesWithGenreStateFlow = MutableStateFlow<ResultResource<List<MovieWithGenres>>>(ResultResource.Loading())
    val moviesWithGenreStateFlow: StateFlow<ResultResource<List<MovieWithGenres>>> = _moviesWithGenreStateFlow

    private val _moviesLikedStateFlow = MutableStateFlow<ResultResource<List<Movie>>>(ResultResource.Loading())
    val moviesLikedStateFlow: StateFlow<ResultResource<List<Movie>>> = _moviesLikedStateFlow

    private val _movieSearchStateFlow = MutableStateFlow<ResultResource<List<MovieWithGenres>>>(ResultResource.Loading())
    val movieSearchStateFlow: StateFlow<ResultResource<List<MovieWithGenres>>> = _movieSearchStateFlow

    var allMoviesWithGenre: List<MovieWithGenres> = emptyList()

    fun getMovieLists() {
        getMovieWithGenreList()
        getMovieLikedList()
    }

    private fun getMovieWithGenreList() {
        viewModelScope.launch {
            getMovieWithGenreUseCase().catch { throwable ->
                _moviesWithGenreStateFlow.value = ResultResource.Failure(Exception(throwable.message))
            }.collect { listMovies ->
                if (!listMovies.isNullOrEmpty()) {
                    allMoviesWithGenre = listMovies
                    _moviesWithGenreStateFlow.value = ResultResource.Success(listMovies)
                } else {
                    _moviesWithGenreStateFlow.value = ResultResource.Failure(Exception("No data"))
                }
            }
        }
    }

    private fun getMovieLikedList() {
        viewModelScope.launch {
            getMovieLikedUseCase().catch { throwable ->
                _moviesLikedStateFlow.value = ResultResource.Failure(Exception(throwable.message))
            }.collect { listMovies ->
                if (!listMovies.isNullOrEmpty()) {
                    _moviesLikedStateFlow.value = ResultResource.Success(listMovies)
                } else {
                    _moviesLikedStateFlow.value = ResultResource.Failure(Exception("Not data"))
                }
            }
        }
    }

    fun filterRecipes(query: String) {
        viewModelScope.launch {
            val filteredList = if (query.isEmpty()) {
                allMoviesWithGenre
            } else {
                allMoviesWithGenre.filter { movie ->
                    movie.movie.title.contains(query, ignoreCase = true)
                }
            }
            _movieSearchStateFlow.emit(ResultResource.Success(filteredList))
        }
    }
}