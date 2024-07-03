package com.alepagani.codechallengemovies.data.remote

import com.alepagani.codechallengemovies.BuildConfig
import com.alepagani.codechallengemovies.data.model.GenreList
import com.alepagani.codechallengemovies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers("Authorization: ${BuildConfig.API_KEY}", "accept: application/json")
    @GET("movie/top_rated")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "es",
        @Query("page") page: Int
    ): MoviesResponse

    @Headers("Authorization: ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("language") language: String = "en"): GenreList
}