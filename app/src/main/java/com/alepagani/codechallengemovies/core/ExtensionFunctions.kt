package com.alepagani.codechallengemovies.core

fun String.getYearFromReleaseDate(): String {
    return this.substring(0, 4)
}