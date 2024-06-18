package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke() = repository.getNowPlayingMovies()
}