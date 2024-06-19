package com.alepagani.codechallengemovies.data

import com.alepagani.codechallengemovies.data.local.LocalMovieDataSource
import com.alepagani.codechallengemovies.data.mapper.toGenreEntity
import com.alepagani.codechallengemovies.data.mapper.toMovieEntity
import com.alepagani.codechallengemovies.data.mapper.toMovieList
import com.alepagani.codechallengemovies.data.model.GenreList
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieWithGenres
import com.alepagani.codechallengemovies.data.remote.RemoteMovieDataSource
import com.alepagani.codechallengemovies.data.remote.util.resultOf
import com.alepagani.codechallengemovies.domain.Repository
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

    override fun getNowPlayingMoviesWithGenres(): Flow<List<MovieWithGenres>> {
        val localFlow = local.getAllMoviesWithGenre()
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
        local.saveMovieLiked(movie.toMovieEntity())
    }

    private fun getMoviesFromApi(): Flow<List<Movie>> {
        return flow {
            resultOf {
                val movies = remote.getMovieList().results
                getGenreFromApi()
                insertMovies(movies)
            }
            emit(emptyList<Movie>())
        }.onStart {
            emit(emptyList())
        }
    }

    private suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            local.insertMovie(movie.toMovieEntity())
            movie.genre_ids.forEach { genre ->
                local.insertMovieGenre(movie.id, genre)
            }
        }
    }

    private suspend fun getGenreFromApi() {
        val genreList = remote.getGenreList()
        insertGenre(genreList)
    }

    private suspend fun insertGenre(genreList: GenreList) {
        genreList.genres.forEach {
            local.insertGenre(it.toGenreEntity())
        }
    }
}