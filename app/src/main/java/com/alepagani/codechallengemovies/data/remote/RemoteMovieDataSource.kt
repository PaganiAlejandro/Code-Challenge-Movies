package com.alepagani.codechallengemovies.data.remote

import com.alepagani.codechallengemovies.data.model.GenreList
import com.alepagani.codechallengemovies.data.model.MovieResponse
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieList(): MovieResponse = movieApi.getNowPlayingMovies()

    suspend fun getGenreList(): GenreList = movieApi.getGenres()
}