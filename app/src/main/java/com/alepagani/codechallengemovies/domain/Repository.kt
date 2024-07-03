package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.MovieGenre
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getNowPlayingMoviesWithGenres(): Flow<List<MovieGenre>>

    fun getMoviesLiked(): Flow<List<MovieGenre>>

    suspend fun getMovie(id: Int): MovieGenre

    suspend fun saveMovieLiked(movieResponse: MovieGenre)
}