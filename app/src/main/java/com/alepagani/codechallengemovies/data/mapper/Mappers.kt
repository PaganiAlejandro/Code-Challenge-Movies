package com.alepagani.codechallengemovies.data.mapper

import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.model.Movie
import com.alepagani.codechallengemovies.data.model.MovieGenre

fun List<MovieEntity>.toMovieList(): List<MovieGenre> {
    val resultList = mutableListOf<MovieGenre>()
    this.forEach {
        resultList.add(it.toMovie())
    }
    return resultList
}

fun MovieEntity.toMovie(): MovieGenre = MovieGenre(
    this.movieId,
    this.adult,
    this.backdrop_path,
    this.genre,
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

fun MovieGenre.toMovieEntity(): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path,
    this.genre,
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

fun Movie.toMovieEntity(genre: String): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path,
    genre,
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