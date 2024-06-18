package com.alepagani.codechallengemovies.data

import android.util.Log
import com.alepagani.codechallengemovies.data.local.LocalMovieDataSource
import com.alepagani.codechallengemovies.data.local.MovieDao
import com.alepagani.codechallengemovies.data.mapper.toMovieEntity
import com.alepagani.codechallengemovies.data.mapper.toMovieEntityLiked
import com.alepagani.codechallengemovies.data.mapper.toMovieList
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.remote.RemoteMovieDataSource
import com.alepagani.codechallengemovies.data.remote.util.resultOf
import com.alepagani.codechallengemovies.domain.Repository
import com.alepagani.codechallengeyape.core.ResultResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val local: LocalMovieDataSource,
    private val remote: RemoteMovieDataSource
): Repository {
    override fun getNowPlayingMovies(): Flow<List<Movie>> {
        val localFlow = local.getAllMovies().map {
            it.toMovieList()
        }
        val apiFlow = getMoviesFromApi()

        return localFlow.combine(apiFlow) {db, _ ->
            db
        }
    }

    override fun getMoviesLiked(): Flow<List<Movie>> {
        return local.getMoviesLiked().map {
            it.toMovieList()
        }
    }

    override suspend fun saveMovieLiked(movie: Movie) {
        local.saveMovieLiked(movie.toMovieEntityLiked())
    }

    private fun getMoviesFromApi(): Flow<List<Movie>> {
        return flow {
            resultOf {
                val movies = remote.getMovieList().results
                Log.d("ALEPASO", movies.size.toString())
                insertMovies(movies)
            }
            emit(emptyList<Movie>())
        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach {
            local.insertMovie(it.toMovieEntity())
        }
    }
}