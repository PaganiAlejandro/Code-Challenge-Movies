package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.MovieGenre
import javax.inject.Inject

class SaveMovieLikedUseCase @Inject constructor(private val repository: Repository){

    operator suspend fun invoke(movieResponse: MovieGenre) = repository.saveMovieLiked(movieResponse)
}