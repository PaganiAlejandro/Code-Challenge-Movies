package com.alepagani.codechallengemovies.data

import android.content.SharedPreferences
import com.alepagani.codechallengemovies.core.AppConstant.LAST_PAGE_KEY
import com.alepagani.codechallengemovies.data.local.LocalMovieDataSource
import com.alepagani.codechallengemovies.data.mapper.toMovie
import com.alepagani.codechallengemovies.data.mapper.toMovieEntity
import com.alepagani.codechallengemovies.data.mapper.toMovieList
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieGenre
import com.alepagani.codechallengemovies.data.remote.RemoteMovieDataSource
import com.alepagani.codechallengemovies.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val local: LocalMovieDataSource,
    private val remote: RemoteMovieDataSource,
    private val sharedPreferences: SharedPreferences
): Repository {

    private var hashGenres = HashMap<Int, String>()

    override suspend fun getNowPlayingMoviesWithGenres(): Flow<List<MovieGenre>> {
        val localFlow = local.getAllMoviesWithGenre()
        getMoviesFromApi()
        return localFlow.map { it.toMovieList() }
    }

    override fun getMoviesLiked(): Flow<List<MovieGenre>> {
        return local.getMoviesLiked().map {
            it.toMovieList()
        }
    }

    override suspend fun getMovie(id: Int): MovieGenre {
        return local.getMovie(id).toMovie()
    }

    override suspend fun saveMovieLiked(movieResponse: MovieGenre) {
        local.saveMovieLiked(movieResponse.toMovieEntity())
    }

    suspend private fun getMoviesFromApi() {
        val movies = remote.getMovieList(getNextPage()).results
        getGenreFromApi()
        insertMovies(movies)
    }

    private fun getNextPage(): Int {
        val nextPage = sharedPreferences.getInt(LAST_PAGE_KEY, 0) + 1
        sharedPreferences.edit().putInt(LAST_PAGE_KEY, nextPage).apply()
        return nextPage
    }

    private suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            val genre = hashGenres.get(movie.genre_ids.first()) ?: ""
            local.insertMovie(movie.toMovieEntity(genre))
        }
    }

    private suspend fun getGenreFromApi() {
        val genreList = remote.getGenreList()
        genreList.genres.forEach {
            hashGenres.put(it.id, it.name)
        }
    }
}