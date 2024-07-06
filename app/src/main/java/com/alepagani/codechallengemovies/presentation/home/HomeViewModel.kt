package com.alepagani.codechallengemovies.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.DeleteMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMoviesUseCase
import com.alepagani.codechallengemovies.domain.SaveMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.getGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getMoviesUseCase: GetMoviesUseCase,
    getMovieLikedUseCase: GetMovieLikedUseCase,
    private val saveMovieLikedUseCase: SaveMovieLikedUseCase,
    private val deleteMovieLiked: DeleteMovieLikedUseCase,
    getGenresUseCase: getGenresUseCase
) : ViewModel() {

    private var _genres = MutableLiveData<HashMap<Int, String>>()
    val genres: LiveData<HashMap<Int, String>> = _genres
    val popularMovies = getMoviesUseCase().cachedIn(viewModelScope)
    val moviesLiked: LiveData<List<Movie>> = getMovieLikedUseCase()

    init {
        viewModelScope.launch {
            _genres.postValue(getGenresUseCase())
        }
    }
    suspend fun saveMovieFavorite(movie: Movie) {
        saveMovieLikedUseCase(movie)
    }

    suspend fun deleteMovieFavorite(movie: Movie) {
        deleteMovieLiked(movie)
    }

    fun filterRecipes(query: String) {
        viewModelScope.launch {
            val filteredList = if (query.isEmpty()) {
                popularMovies.value
            } else {
                popularMovies.value?.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
        }
    }
}