package com.alepagani.codechallengemovies.data.local

import androidx.lifecycle.LiveData
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val dao: MovieDao) {

    fun getMoviesLiked(): LiveData<List<MovieEntity>> {
        return dao.getFavoritesMovies()
    }
    suspend fun saveMovieLiked(movie: MovieEntity) {
        dao.insertMovie(movie)
    }

    suspend fun removeMovieLiked(movie: MovieEntity) {
        dao.deleteMovieLiked(movie)
    }
}

