package com.alepagani.codechallengemovies.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.alepagani.codechallengemovies.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getMoviesFromApi(): PagingSource<Int, Movie>

    suspend fun getMovieFromApi(movieId: Int): Movie

    fun getMoviesLiked(): LiveData<List<Movie>>

    suspend fun saveMovieLiked(movie: Movie)

    suspend fun deleteMovieLiked(movie: Movie)

    suspend fun getGenreFromApi(): HashMap<Int, String>
}