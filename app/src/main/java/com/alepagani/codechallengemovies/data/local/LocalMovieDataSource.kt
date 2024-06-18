package com.alepagani.codechallengemovies.data.local

import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val dao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> {
        return dao.getAllMovies()
    }

    fun getMoviesLiked(): Flow<List<MovieEntity>> {
        return dao.getAllMoviesLiked()
    }
    suspend fun insertMovie(movie: MovieEntity) {
        dao.insertMovie(movie)
    }

    suspend fun saveMovieLiked(movie: MovieEntity) {
        dao.saveMovieLike(movie)
    }
}