package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class GetMovieUseCase @Inject constructor(val repository: Repository) {

    operator suspend fun invoke(movieId: Int) = repository.getMovieFromApi(movieId)
}