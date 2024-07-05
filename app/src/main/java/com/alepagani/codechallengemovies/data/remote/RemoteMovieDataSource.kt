package com.alepagani.codechallengemovies.data.remote

import com.alepagani.codechallengemovies.data.model.GenreList
import com.alepagani.codechallengemovies.data.model.MoviesResponse
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieList(page: Int): MoviesResponse = movieApi.getNowPlayingMovies("es", page)

    suspend fun getGenreList(): GenreList = movieApi.getGenres()

    suspend fun getMoviesSearch(query: String, page: Int): MoviesResponse = movieApi.searchMovies(query, page)
}