package com.alepagani.codechallengemovies.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.alepagani.codechallengemovies.core.AppConstant.LAST_PAGE_KEY
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.DeleteMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.GetMoviesUseCase
import com.alepagani.codechallengemovies.domain.SaveMovieLikedUseCase
import com.alepagani.codechallengemovies.domain.getGenresUseCase
import com.alepagani.codechallengeyape.core.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieLikedUseCase: GetMovieLikedUseCase,
    private val saveMovieLikedUseCase: SaveMovieLikedUseCase,
    private val deleteMovieLiked: DeleteMovieLikedUseCase,
    private val getGenresUseCase: getGenresUseCase
) : ViewModel() {

    private lateinit var genres: HashMap<Int, String>
    val popularMovies = getMoviesUseCase().cachedIn(viewModelScope)

    val moviesLiked: LiveData<List<Movie>> = getMovieLikedUseCase()

    private var moviesFiltered: List<Movie> = emptyList()

    init {
        viewModelScope.launch {
            genres = getGenresUseCase()
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