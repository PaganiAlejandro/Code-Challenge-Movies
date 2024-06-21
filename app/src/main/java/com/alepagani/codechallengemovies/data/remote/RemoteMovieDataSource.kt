package com.alepagani.codechallengemovies.data.remote

import com.alepagani.codechallengemovies.data.model.GenreList
import com.alepagani.codechallengemovies.data.model.MovieResponse
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getMovieList(page: Int): MovieResponse = movieApi.getNowPlayingMovies("es", page)

    suspend fun getGenreList(): GenreList = movieApi.getGenres()
}