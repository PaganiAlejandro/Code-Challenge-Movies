package com.alepagani.codechallengemovies.data.remote

import com.alepagani.codechallengemovies.BuildConfig
import com.alepagani.codechallengemovies.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers("Authorization: ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResponse
}