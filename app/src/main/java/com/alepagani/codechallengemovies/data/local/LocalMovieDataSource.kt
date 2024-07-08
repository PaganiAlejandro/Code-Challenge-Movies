package com.alepagani.codechallengemovies.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val dao: MovieDao) {

    fun getMoviesLiked(): Flow<List<MovieEntity>> {
        return dao.getFavoritesMovies()
    }
    suspend fun saveMovieLiked(movie: MovieEntity) {
        dao.insertMovie(movie)
    }

    suspend fun isMovieLiked(movieId: Int): Boolean {
        val movie = dao.isMovieLiked(movieId)
        movie?.let {

            Log.d("ale paso", "es true ${it}")
            return true
        }
        Log.d("ale paso", "es false")
        return false
    }

    suspend fun removeMovieLiked(movie: MovieEntity) {
        dao.deleteMovieLiked(movie)
    }
}

