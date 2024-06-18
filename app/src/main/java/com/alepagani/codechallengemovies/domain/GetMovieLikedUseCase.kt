package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class GetMovieLikedUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke() = repository.getMoviesLiked()
}