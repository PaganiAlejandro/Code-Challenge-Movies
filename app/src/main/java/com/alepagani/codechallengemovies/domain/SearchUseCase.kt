package com.alepagani.codechallengemovies.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(query: String) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { repository.searchMoviesPagingSource(query) }
    ).liveData
}