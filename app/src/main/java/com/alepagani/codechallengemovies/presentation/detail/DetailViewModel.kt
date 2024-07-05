package com.alepagani.codechallengemovies.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.SaveMovieLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveMovieLikedUseCase: SaveMovieLikedUseCase,
  //  private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    private var _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> = _isLiked

    fun setMovie(movieId: Int) {
        // viewModelScope.launch {
        //     _movie.postValue(getMovieUseCase(movieId))
        //     _isLiked.postValue(_movie.value?.is_liked ?: false)
        // }
    }

    fun saveMovieLiked() {
        _isLiked.postValue(movie.value?.is_liked?.not())
        movie.value?.is_liked = movie.value?.is_liked?.not() ?: false
        viewModelScope.launch {
            movie?.value?.let {
                saveMovieLikedUseCase(it)
            }
        }
    }
}