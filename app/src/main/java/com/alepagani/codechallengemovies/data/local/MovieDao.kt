package com.alepagani.codechallengemovies.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_favorites")
    fun getFavoritesMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies_favorites WHERE movieId = :movieId")
    suspend fun isMovieLiked(movieId: Int): MovieEntity

    @Delete
    suspend fun deleteMovieLiked(movie: MovieEntity)
}