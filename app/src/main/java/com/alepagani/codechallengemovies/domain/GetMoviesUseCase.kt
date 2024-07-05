package com.alepagani.codechallengemovies.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {  repository.getMoviesFromApi() }
    ).liveData
}