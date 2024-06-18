package com.alepagani.codechallengemovies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alepagani.codechallengemovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentity where is_liked = 1")
    fun getAllMoviesLiked(): Flow<List<MovieEntity>>

    @Update
    suspend fun saveMovieLike(movie: MovieEntity)

    @Delete
    suspend fun deleteMovieLike(movie: MovieEntity)
 }