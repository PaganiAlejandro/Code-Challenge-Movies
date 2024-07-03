package com.alepagani.codechallengemovies.domain

import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getNowPlayingMoviesWithGenres(): Flow<List<MovieWithGenres>>

    fun getMoviesLiked(): Flow<List<Movie>>

    suspend fun getMovie(id: Int): MovieWithGenres

    suspend fun saveMovieLiked(movie: Movie)
}