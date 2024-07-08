package com.alepagani.codechallengemovies.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.DeleteMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMovieUseCase
import com.alepagani.codechallengemovies.domain.IsLikedUseCase
import com.alepagani.codechallengemovies.domain.SaveMovieLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveMovieLikedUseCase: SaveMovieLikedUseCase,
    private val deleteMovieLikedUseCase: DeleteMovieLikedUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val isLikedUseCase: IsLikedUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    private var _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> = _isLiked

    fun setMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.postValue(getMovieUseCase(movieId))
        }
        viewModelScope.launch {
            val lala = isLikedUseCase(movieId)
            Log.d("ale paso", "islikes dentro del vm ${lala}")
            _isLiked.postValue(lala)
        }
    }

    fun saveMovieLiked() {
        _isLiked.postValue(isLiked.value?.not())
        viewModelScope.launch {
            movie.value?.let {
                if (isLiked.value == false) {
                    Log.d("ale paso", "Inserta la peli")
                    saveMovieLikedUseCase(it)
                } else {
                    Log.d("ale paso", "Elimina la peli")
                    deleteMovieLikedUseCase(it)
                }
            }
        }
    }
}