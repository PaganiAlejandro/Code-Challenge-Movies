package com.alepagani.codechallengemovies.data.model

data class MoviesResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)