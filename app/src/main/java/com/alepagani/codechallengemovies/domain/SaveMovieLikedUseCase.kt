package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import javax.inject.Inject

class SaveMovieLikedUseCase @Inject constructor(private val repository: Repository){

    operator suspend fun invoke(movie: Movie) = repository.saveMovieLiked(movie)
}