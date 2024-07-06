package com.alepagani.codechallengemovies.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alepagani.codechallengemovies.domain.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
): ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    // val searchResults = Transformations.switchMap(_query) {
    //     searchMoviesUseCase(it).cachedIn(viewModelScope)
    // }

    fun setQuery(query: String) {
        _query.value = query
    }


    // init {
    //     viewModelScope.launch {
    //         refreshGenresUseCase()
    //     }
    // }

}