package com.alepagani.codechallengemovies.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.GetMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMoviesUseCase
import com.alepagani.codechallengemovies.domain.SaveMovieLikedUseCase
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
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieLikedUseCase: GetMovieLikedUseCase
) : ViewModel() {

    private val _moviesStateFlow = MutableStateFlow<ResultResource<List<Movie>>>(ResultResource.Loading())
    val moviesStateFlow: StateFlow<ResultResource<List<Movie>>> = _moviesStateFlow

    private val _moviesLikedStateFlow = MutableStateFlow<ResultResource<List<Movie>>>(ResultResource.Loading())
    val moviesLikedStateFlow: StateFlow<ResultResource<List<Movie>>> = _moviesLikedStateFlow

    init {
        getMovieList()
        getMovieLikedList()
    }

    private fun getMovieList() {
        Log.d("PASARAAAA EN EL VIEWMODEL", "PASOOO")
        viewModelScope.launch {
            getMoviesUseCase().catch { throwable ->
                _moviesStateFlow.value = ResultResource.Failure(Exception(throwable.message))
            }.collect { listMovies ->
                if (!listMovies.isNullOrEmpty()) {
                    _moviesStateFlow.value = ResultResource.Success(listMovies)
                } else {
                    _moviesStateFlow.value = ResultResource.Failure(Exception("Not data"))
                }
            }
        }
    }

    fun getMovieLikedList() {
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
}