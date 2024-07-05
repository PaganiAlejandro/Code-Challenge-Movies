package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import javax.inject.Inject

class getGenresUseCase @Inject constructor(val repository: Repository) {

    operator suspend fun invoke() = repository.getGenreFromApi()
}