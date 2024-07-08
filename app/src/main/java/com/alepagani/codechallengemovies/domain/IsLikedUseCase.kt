package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class IsLikedUseCase @Inject constructor(val repository: Repository) {

    operator suspend fun invoke(movieId: Int): Boolean = repository.isMovieLiked(movieId)
}