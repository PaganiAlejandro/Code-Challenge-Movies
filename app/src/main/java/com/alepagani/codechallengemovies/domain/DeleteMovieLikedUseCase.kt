package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import javax.inject.Inject

class DeleteMovieLikedUseCase @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(movie: Movie) = repository.deleteMovieLiked(movie)
}