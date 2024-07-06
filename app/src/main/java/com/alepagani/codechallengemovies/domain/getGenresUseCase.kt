package com.alepagani.codechallengemovies.domain

import javax.inject.Inject

class getGenresUseCase @Inject constructor(val repository: Repository) {

    operator suspend fun invoke(): HashMap<Int, String> = repository.getGenreFromApi()
}