package com.alepagani.codechallengemovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alepagani.codechallengemovies.data.local.entity.GenreEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import com.alepagani.codechallengemovies.data.local.entity.MovieGenreCrossRef

@Database(entities = [MovieEntity::class, GenreEntity::class, MovieGenreCrossRef::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}