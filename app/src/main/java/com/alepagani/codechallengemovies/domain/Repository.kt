package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import com.alepagani.codechallengeyape.core.ResultResource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getNowPlayingMoviesWithGenres(): Flow<List<MovieWithGenres>>

    fun getMoviesLiked(): Flow<List<Movie>>

    suspend fun saveMovieLiked(movie: Movie)
}