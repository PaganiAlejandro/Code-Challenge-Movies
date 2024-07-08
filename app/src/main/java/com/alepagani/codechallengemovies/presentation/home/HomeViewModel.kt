package com.alepagani.codechallengemovies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.GetMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMoviesUseCase
import com.alepagani.codechallengemovies.domain.getGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    getMovieLikedUseCase: GetMovieLikedUseCase,
    getGenresUseCase: getGenresUseCase
) : ViewModel() {

    private var _genres = MutableLiveData<HashMap<Int, String>>()
    val genres: LiveData<HashMap<Int, String>> = _genres
    val popularMovies = getMoviesUseCase().cachedIn(viewModelScope)
    val moviesLiked: Flow<List<Movie>> = getMovieLikedUseCase()

    init {
        viewModelScope.launch {
            _genres.postValue(getGenresUseCase())
        }
    }
}