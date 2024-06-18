package com.alepagani.codechallengemovies.data.mapper

import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.model.Movie

fun List<MovieEntity>.toMovieList(): List<Movie> {
    val resultList = mutableListOf<Movie>()
    this.forEach {
        resultList.add(it.toMovie())
    }
    return resultList
}

fun MovieEntity.toMovie(): Movie = Movie(
    this.id,
    this.adult,
    this.backdrop_path,
    //this.genre_ids,
    this.original_language,
    this.original_title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    this.is_liked
)

fun Movie.toMovieEntity(): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path,
    //this.genre_ids,
    this.original_language,
    this.original_title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    this.is_liked ?: false
)

fun Movie.toMovieEntityLiked(): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path,
    //this.genre_ids,
    this.original_language,
    this.original_title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count,
    true
)