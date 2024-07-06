package com.alepagani.codechallengemovies.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alepagani.codechallengemovies.data.model.Genre
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.domain.SearchMoviesUseCase
import com.alepagani.codechallengemovies.domain.getGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    getGenresUseCase: getGenresUseCase
): ViewModel() {
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    private var _genres = MutableLiveData<HashMap<Int, String>>()
    val genres: LiveData<HashMap<Int, String>> = _genres

    private val _searchResults = MediatorLiveData<PagingData<Movie>>()
    val searchResults: LiveData<PagingData<Movie>> get() = _searchResults

    fun setQuery(query: String) {
        _query.value = query
    }

    init {
        _searchResults.addSource(_query) { query ->
            searchMovies(query)
        }

        viewModelScope.launch {
            _genres.postValue(getGenresUseCase())
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            searchMoviesUseCase(query).cachedIn(viewModelScope).observeForever {
                _searchResults.postValue(it)
            }
        }
    }
}