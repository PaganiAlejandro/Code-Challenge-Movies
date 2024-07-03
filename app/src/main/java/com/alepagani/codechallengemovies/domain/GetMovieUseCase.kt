package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke(id: Int) = repository.getMovie(id)
}