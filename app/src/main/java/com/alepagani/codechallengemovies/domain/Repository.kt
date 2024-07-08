package com.alepagani.codechallengemovies.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.alepagani.codechallengemovies.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getMoviesFromApi(): PagingSource<Int, Movie>

    suspend fun getMovieFromApi(movieId: Int): Movie

    fun getMoviesLiked(): Flow<List<Movie>>

    suspend fun isMovieLiked(movieId: Int): Boolean

    suspend fun saveMovieLiked(movie: Movie)

    suspend fun deleteMovieLiked(movie: Movie)

    suspend fun getGenreFromApi(): HashMap<Int, String>

    fun searchMoviesPagingSource(query: String): PagingSource<Int, Movie>
}