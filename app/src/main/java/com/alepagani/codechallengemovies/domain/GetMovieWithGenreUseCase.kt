package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class GetMovieWithGenreUseCase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke() = repository.getNowPlayingMoviesWithGenres()
}