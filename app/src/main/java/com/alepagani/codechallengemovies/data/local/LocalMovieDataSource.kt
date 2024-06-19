package com.alepagani.codechallengemovies.data.local

import com.alepagani.codechallengemovies.data.local.entity.GenreEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieGenreCrossRef
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val dao: MovieDao) {

    fun getAllMoviesWithGenre(): Flow<List<MovieWithGenres>> {
        return dao.getAllMovieWithGenres()
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

    suspend fun insertGenre(genre: GenreEntity) {
        dao.insertGenre(genre)
    }

    suspend fun insertMovieGenre(movieId: Int, genreId: Int) {
        dao.insertMovieGenreCrossRef(MovieGenreCrossRef(movieId, genreId))
    }
}

