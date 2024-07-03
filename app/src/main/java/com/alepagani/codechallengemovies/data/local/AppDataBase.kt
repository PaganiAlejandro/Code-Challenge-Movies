package com.alepagani.codechallengemovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}