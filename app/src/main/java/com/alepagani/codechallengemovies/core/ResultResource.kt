package com.alepagani.codechallengeyape.core

sealed class ResultResource<out T> {
    class Loading<out T>: ResultResource<T>()
    data class Success<out T>(val data: T): ResultResource<T>()
    data class Failure(val exception: Exception) : ResultResource<Nothing>()
}