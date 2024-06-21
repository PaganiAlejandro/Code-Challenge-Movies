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
class DetailViewModel @Inject constructor(private val saveMovieLikedUseCase: SaveMovieLikedUseCase) : ViewModel() {

    private lateinit var movieDetail: Movie
    private var _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> = _isLiked

    fun setMovie(movie: Movie) {
        movieDetail = movie
        _isLiked.postValue(movie.is_liked ?: false)
    }

    fun saveMovieLiked() {
        _isLiked.postValue(movieDetail.is_liked?.not())
        movieDetail.is_liked = movieDetail.is_liked?.not()
        viewModelScope.launch {
            movieDetail?.let {
                saveMovieLikedUseCase(it)
            }
        }
    }
}